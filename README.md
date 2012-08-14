#Welcome to Agorava Socializer (Wicket version)
This is a Wicket version of [Agorava's Socializer](https://github.com/agorava/agorava-socializer) app. Socializer is a demonstration Web App for Agorava. It allows you to connect to various social Media, and see your timeline and post update.

##Running
Socializer comes with a Jetty configuration, so you can lauch the webapp directly from maven. All you have to do is enter the command

`mvn -Prun`

and wait for maven to download the world.
When Jetty is launched, just point your browser to
`http://localhost:8080`
and enjoy.

You can also create an Eclipse project via the maven command

`mvn eclipse:eclipse`

Then you can import the project into Eclipse and run the app via the Start class.

##Note
This project is not officially connected to the Agorava team. I am currently working on this sporadically as a slack time project for my job at [42 Lines](http://42lines.net). We're looking for awesome Wicket developers if you are one and are looking for an awesome place to work.