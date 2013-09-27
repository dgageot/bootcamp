[Project sur Github](https://github.com/xebia-france/code-elevator)


## Rules

The goal of the game is to implement an elevator engine. Participants have to subscribe with a login, an email (in order
to display a linked [gravatar](http://www.gravatar.com)) and a server url. Then HTTP GET requests will be send to this
server :

### events (just respond HTTP 200 return code)

- `/call?atFloor=[0-5]&to=[UP|DOWN]`
- `/go?floorToGo=[0-5]`
- `/userHasEntered`
- `/userHasExited`
- `/reset?cause=information+message`

### response

- `/nextCommand` : body of the request must contains `NOTHING`, `UP`, `DOWN`, `OPEN` or `CLOSE`

## Running the server locally

### Prerequisites

Here is what you need to build and run a code elevator session :

- JDK 1.7
- maven 3.x

### Steps

    $ git clone git@github.com:xebia-france/code-elevator.git
    $ cd code-elevator
    $ mvn clean install
    $ mvn --file elevator-server/pom.xml jetty:run

Go to [http://localhost:8080](http://localhost:8080), subscribe to a session and start implementing your elevator
server.

Once already is ok for all participants, you should increase max number of users per building which is zero at first. To
perform this go to [http://localhost:8080/#/administration](http://localhost:8080/#/administration), log in with user
`admin` and password which has been logged out on standard output of server.

## Running on a remote server

Don't want to install Java nor fill up your hard drive with jar files you can try [Sebastian's online server](http://code-elevator.seblm.cloudbees.net/#/)
