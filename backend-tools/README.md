# timetrack backend-tools
Tools for dealing with the application.


## Use it
**Options**

* `-t` - createTestData - create test data 
  * `-u` - userCount - amount of users to create
  * `-w` - worklogCount - max amount of worklog entries per user
* `-r` - wipe test data


**Create test data**

Run the jar `java -jar backend-tools-{version}.jar -t -u={userCount} -w={worklogCount} --spring.profiles.active={profile}`


**Wipe test data**
Run the jar `java -jar backend-tools-{version}.jar -r --spring.profiles.active={profile}`


---
[to project root](https://github.com/dwalldorf/timetrack)