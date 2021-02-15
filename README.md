### build
```bash
gradle clean fatJar
```
### jar location:
```bash
build/libs/couchbase-1.0-SNAPSHOT.jar
```
### Usage
```bash
java -jar build/libs/couchbase-1.0-SNAPSHOT.jar
    
    Enter file or folder name: 
    e.g. /Users/djchen/work/couch_jsons
    
    Enter optional regex pattern: 
    e.g. (.*)file(.*) or <return> to load all files in directory specified above
```
### resources/config.json
```
{ 
    "connection" : "127.0.0.1",
    "username" : "Administrator",
    "password" : "passw0rd",
    "bucket" : "default"
}
```