# Basic operation system
all OS supported by Google App Engine

# Required Software
Netbeans IDE 7.4 (alternativ: Eclipse)
Google App Engine SDK for Java 1.8.9 (http://googleappengine.googlecode.com/files/appengine-java-sdk-1.8.9.zip)
Netbeans App Engine Plugin for Netbeans 7.4 (https://nb-gaelyk-plugin.googlecode.com/files/nbappengine-7.4.x-gae1.8.x-3.0.2.zip)

# Run scripts
Build Project
Run Application
Point browser to http://localhost:8080/ for the frontend
Point browser to http://localhost:8080/_ah/admin for the google SDK admin interface


# Setup
Download and unzip the App Engine SDK 
Download and unzip the App Engine Plugin
In Netbeans, install the plug-in via Tools -> Plugins -> Downloaded
In Netbeans, under Services -> Servers create a new App Engine Server 
- right-click -> add Server -> Google App Engine
- choose your unzipped SDK folder as Location and finish
Checkout branch gae via https://github.com/tarator/AIC13G3P3.git 
Create a project dialog: Java Web -> from existing sources
Provide location of source, project name and project folder
Choose Google App Engine as Server


# Available features
- TaskQueue for queueing sentiment queries 
- Backend instances for processing the sentiment queries
- Frontend at http://localhost:8080/
- Creation of companies & queries via frontend