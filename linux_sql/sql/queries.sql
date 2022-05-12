SELECT cpu_number,id AS host_id,total_mem
FROM host_info
GROUP BY cpu_number, id
ORDER BY total_mem DESC;


CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;


SELECT 
	host_id, 
	hostname AS host_name, 
	round5(host_usage.timestamp) AS timestamp, 
	ROUND(AVG(total_mem - memory_free*1024)*100/total_mem) AS avg_used_mem_percentage
FROM host_usage 
	INNER JOIN host_info ON id = host_id 
GROUP BY host_id, round5(host_usage.timestamp), total_mem, hostname;


SELECT host_id,
	round5(timestamp) AS ts, 
	count(round5(timestamp)) AS num_data_points
FROM host_usage 
GROUP BY host_id, round5(timestamp) 
	HAVING count(round5(timestamp)) < 3;
