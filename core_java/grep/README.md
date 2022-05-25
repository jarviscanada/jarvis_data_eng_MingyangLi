# Introduction
Implemented a simple grep app in Java.
The app searches for a text pattern recursively in a given directory,
and output matched lines to a file. Use ***slf4j*** library for log,
and ***regex*** library to search the pattern.
Reimplemented the methods that return a list of all files in a given directory
and a list of all strings in a file using ***Lambda*** and ***Stream APIs***.
Dockerize the grep app by ***docker*** and push the image to Docker Hub for easier distribution.


# Quick Start
```
regex_pattern=".*Romeo.*Juliet.*"
src_dir="./data"
outfile=grep_$(date +%F_%T).txt
docker run --rm \
-v `pwd`/data:/data -v `pwd`/out:/out limingy9/grep \
${regex_pattern} ${src_dir} /out/${outfile}
cat out/$outfile
```

# Implemenation
## Pseudocode
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
While the app is processing a very complex directory or a very large file,
the JVM could run out of heap memory. In that case, we can set bigger JVM heap size,
or set the garbage collector to perform more often.


# Test
- Test `listFiles` method by wrong directory, empty directory and complex nested directory.
- Test `readLines` method by empty file and `shakespeare.txt`.

# Deployment
1. Create dockerfile
2. Package java app
3. Build a new docker image locally
4. Push the image to Docker Hub

# Improvement
1. No file name before the lines compared with grep.
2. Usage of the app requires multiple of command lines.
3. Docker run cannot use absolute path.