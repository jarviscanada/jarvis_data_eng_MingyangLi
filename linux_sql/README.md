# Linux Cluster Monitoring Agent
# Introduction
Commonly, ***a cluster of internally connected Linux servers*** is used by any IT company. It is always a question that how to allocate recourse most efficiently among those servers. Hence, collecting their usage is necessary for future resource planning purposes. This project will be able to
- ***provision a PostgreSQL instance***,
- ***create database tables***,
- ***collect all hardware information of each server***, 
- ***keep tracking their usage for each minute***, 
- ***record all the data into two database tables respectively***,
- and ***perform data analytics***. 

The technologies we will use are:
1. ***git*** (Github for version control i.e. GitFlow)
2. ***bash*** (bash scripts for data collection and docker)
3. ***docker*** (PostgreSQL database instance container)
4. ***PostgreSQL CLI tool*** (database connection and sql file/statement execution)
5. ***DDL*** (PostgreSQL Data Definition Language for schemas)
6. ***SQL queries*** (data analytics)

# Quick Start
## Start a psql instance using psql_docker.sh
```
./scripts/psql_docker.sh start
```
## Create tables using ddl.sql
```
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```
## Insert hardware specs data into the DB using host_info.sh
```
bash scripts/host_info.sh localhost 5432 host_agent postgres password
```
## Insert hardware usage data into the DB using host_usage.sh
```
bash scripts/host_usage.sh localhost 5432 host_agent postgres password
```
## Crontab setup
1. edit crontab jobs
```
crontab -e
```
2. add this to crontab
```
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log
```

# Implemenation
1. Understand the overall architecture and design by drawing the architecture diagram and explaining the purpose of each file in the project directory, so I can implement the project correctly.
2. Set up a psql instance using `psql_docker.sh` so I can use it on my local machine during the development.
3. Design two tables to persist hardware specifications data (`host_info` table) and resource usage data (`host_usage` table) into the `host_agent` database of the psql instance `postgres` to perform data analytics, and create a `ddl.sql` script that will automate the database initialization to eliminate all manual processes.
4. Write a monitoring agent program using Bash scripts that will be installed on each server to automatically collect and insert both hardware specification data (`host_info.sh`) and resource usage data (`host_usage.sh`) into the database, and use Linux `crontab` to execute `host_usage.sh` every minute.
5. Solve business questions by `queries.sql` so that clients can manage the cluster better and also plan for future recourses.

## Architecture
![Linux_sql Architecture](https://user-images.githubusercontent.com/37160115/168397304-d7ed9d48-8fa7-41b1-964b-1226723f698e.png)

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh: A script to create/start/stop the psql container.
```
./scripts/psql_docker.sh start|stop|create [db_username][db_password]
```

- host_info.sh: A script to insert the host's hardware specification data into the psql instance.
```
bash scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```

- host_usage.sh: A script to insert the host's resource usage data into the psql instance.
```
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```

- crontab: Execute host_usage.sh every minute, so it collects data continuously.
```
#edit crontab jobs
crontab -e

#add this to crontab
* * * * * bash /home/centos/dev/jrvs/bootcamp/linux_sql/host_agent/scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log

#list crontab jobs
crontab -l
```

- queries.sql:
1. Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
2. Average used memory in percentage over 5 mins interval for each host.
3. Detect host failures whenever it inserts less than three data points within a 5-min interval.
```
psql -h HOST_NAME -p 5432 -U USER_NAME -d DB_NAME -f sql/queries.sql
```

## Database Modeling
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`

| Columns | Description |
| ----- | ----- |
| id | auto-increment, primary key |
| hostname | fully qualified hostname |
| cpu_number | number of CPU |
| cpu_architecture | Processor Architecture |
| cpu_model | model of CPU |
| cpu_mhz | CPU clock speed |
| L2_cache | level 2 CPU cache memory |
| total_mem | total memory |
| timestamp | UTC timestamp |

- `host_usage`

| Columns | Description |
| ----- | ----- |
| timestamp | UTC timestamp |
| host_id | id of the host, foreigner key |
| memory_free | free memory |
| cpu_idle | idle time percentage |
| cpu_kernel | kernel time percentage |
| disk_io | number of disk I/O |
| disk_available | root directory available disk in MB |

# Test
If there are some errors while running the script, go to the line with the error. Run bash CLI in the terminal one by one. If the cmd includes pipes, run it pipe by pipe. Then I can locate the bug, and try to fix it.
For SQL queries, I inserted some sample data generated on purpose. For example, the sample data includes different `cpu_number`s and different `total_mem`s to test group hosts by hardware info query.

# Deployment
Github is a good platform to apply GitFlow for version control.
The crontab cmds can be used to repeat some commands in the specified schedule.
The docker containers are lightweight and suited for running multiple applications over a single operating system kernel. It has OS-level process isolation and boots in seconds, which is exactly what we need to build the database inside.

# Improvements
- Handle hardware update 
- Since the crontab will keep running, `host_usage` will keep growing. It would be nice to have old data deleted at some point.
- Since the program records the usage data once per minute, it may miss some abnormal usage data that is too brief to capture. So we could improve the accuracy by recording the average usage which is calculated by data of every second in a minute.
