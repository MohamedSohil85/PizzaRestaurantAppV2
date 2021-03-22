FROM java:11
MAINTAINER="mohamedsohil1985@gmail.com"
VOLUME /tmp
EXPOSE 8080
ADD target/PizzaRestaurantApp.jar PizzaRestaurantApp.jar
ENTRYPOINT ["java","-jar","PizzaRestaurantApp.jar"]
