# Hazelcast-Vertx-ClusterMgrDemo
A demo for Hazelcast Cluster Management on Vertx
## Description
Hi there, welcome to my Hazelcast-Vertx-Cluster Management Demo. Well, just a small project to kill time in weekends.

Its main verticle is a Http server whose a GET request handler sending a message to a Worker every time it receive new request.

The worker has some calculations which take times to response back to main verticle.

The aim is to let main verticle reaches a state which it's unable to handle anymore request because all of workers in pool are used up.

Then, Cluster design jumps in and demonstrate how it will solve the problem. 
## Project Architecture (Maven project)

.src
     
     \
      
      |--main
       
       \
        
        |--Launcher.java     //ClusterManager and Clustered initialization
        
        |--DemoWorker.java   //A Worker which has to take time to response
        
        |--DemoVerticle.java //A Standard verticle which deploys an HttpServer
       
       /
     
     |--resource
      
      \
       
       |--default-cluster.xml  //Configuration XML file for Hazelcast
