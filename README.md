AIC13G3P3
=========

# Get the code

```
git clone https://github.com/tarator/AIC13G3P3.git
```

# Basic operating system

Kubuntu 13.04

# Required Software

* Java 7 JDK (Basically any JDK e.g. openjdk or Oracle)
* Maven 3.1.1
* Git 1.8.3.2
* MySQL Server 5.5.35-0ubuntu0.13.10.1

Use the following command to install the necessary software on your Kubuntu Box:
```
sudo apt-get install openjdk-7-jdk openjdk-7-jre maven mysql-server mysql-client mysql-common
```


# Instructions for CloudScale and Amazon WS  

## Checkout the right branch from github

```
git clone https://github.com/tarator/AIC13G3P3.git
git checkout aws
```

## Setup and Configuration

* Setup Mysql, create Database 'AIC13G3P3' (utf8_bin) and User 'AIC13G3P3' with password 'AIC13G3P3' and grant all rigths to the database.
* Copy file '/src/main/resources/aic13g3p3.proerties.template' to '/src/main/resources/aic13g3p3.properties' and adjust the necessary configurations.
* Copy file '/src/main/resources/twitter4j.proerties.template' to '/src/main/resources/twitter4j.properties' and fill in the secrets Twitter.
* Copy file '/src/main/resources/aws.proerties.template' to '/src/main/resources/aws.properties' and fill in the secrets for Amazon AWS.
* You may change MySQL Config here: /src/main/webapp/WEB-INF/dispatcher-servlet.xml
* If you don't want the DB to be created from scratch every time you start the application, you may also change the property "hibernate.hbm2ddl.auto" from "create-drop" to "update" in the file "dispatcher-servlet.xml" AND in the file '/src/main/java/at/ac/tuwien/infosys/aic13/cloudscale/service/SentimentAnalysisService.java'

## Start the application

* You have to start webapp and cloudscale-app seperately (e.g. use two different terminals):
* Start Jetty and webapp: run __mvn compile jetty:run__ from the project's main directory.
* Start cloudscale app: run __mvn exec:exec__
* Start your browser and type __http://localhost:8080/G3P3/__
* To stop the system write __end__ into the java-console of the cloudscale-app (Important, else you have to kill all processes manually).



# Instructions for CloudScale and Google PaaS

# Discussion
* Goole group: https://groups.google.com/forum/#!forum/aic13g3p3
# Get Help
More Infos in the [wiki](https://github.com/tarator/AIC13G3P3/wiki)
