# Mars explorer
A Clojure program that simulates the creation of explorers and the mars land available to explore.

## Table content
* [How it works](#how-does-it-works)
* [Built with](#built-with)
* [Getting started](#getting-started)
* [Docker support](#docker-support)
* [Code and design decisions](#code-and-design-decisions)
* [Improvements](#improvements)
* [Business decisions](#business-decisions)
* [Other apps](#other-apps)

## How does it works?
By reading some data from a text file, the program will create explorers and send instructions of movement to them.

A valid text file should follows this sequence:

* **First line:** Describes top right coordinate of mars land. Must contain two numerical values (>= 0) separeted by space. **Ex: 4 3**
* **Second line:** Describes an explorer (its position and face direction). Must contain two numerical values (>= 0) separeted by space and a char indicating which direction the explorer is facing (**N** for north, **E** for east, **W** for west and **S** for south). **Ex: 1 2 N**
* **Third line:** Describes instructions of movement to be send to an explorer. Must contain a string of valid instructions. Valid values are **L** to turn left, **R** to turn right, and **M** to move forward. **Ex: MMLLMRM**

A valid file will looks like the following:
```
10 15
1 1 N
LLMMMRM
```

You can also create more than one explorer with a file like this:
```
10 15
1 1 N
LLMMMRM
3 8 W
MMRRMMLM
```

## Built With
* [Clojure](https://clojure.org/)
* [Leiningen](https://leiningen.org/)
* [Midje](https://github.com/marick/Midje)

## Getting Started

After clone the project, the instructions bellow will get you a copy of the project up and running on your local machine for development and testing purposes.

## Prerequisites

What things you need to install to run this project

* [Java](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Clojure](https://clojure.org/guides/getting_started)
* [Leiningen](https://leiningen.org/)

## Restore dependencies
```
lein deps
```

## Run the application

You need to inform the input file path.

```
lein run PATH_TO_INPUT_FILE
```

There is one input file as a sample on the root of the project, you can use it like this:
```
lein run file-sample
```

After execute the above command, you should see the following output:
![Output result](output-result.png)

## Docker support
It is possible to run the program in a Docker container by running the following commands in the root of the project:

```
docker build -t mars-explorer .
```
```
docker run mars-explorer
```

## Running the tests

To run all the tests
```
lein midje
```

# Code and design decisions
The only lib I use in this project was `midje` for unit tests. All other things are built in in the language.

First thing I did was to decide how I would want the data to come to the business logic functions. Once decided, I started to write test cases and the functions in the logic file itself.

As the input comes from a text file and it is not in an easy pattern, I created an `adapter` layer which has the knowledge to get the content from the input file and convert them into a format the business logic know. The adapter layer is also reponsible to convert a position into a format the caller is expecting.

The config file has the initial configuration the program need. Some constants and some specifications using regex which is used by the adapter layer to validate the input data. I am using `Clojure spec` as a tool to validate the integrity of input data.

The controller layer is just the "glue" between logic and other layers. The only thing the controller knows is **who knows what**.

# Improvements
Some things I'd like to improve are:
* Remove the adapter dependency in controller.
* Improve error messages for invalid input.
* Think about transform `turn` into just a constant with a map.

# Business decisions
I assumed some business definitions by myself. In a team, these definitions should be decided together with team members.

* If an instruction result in an invalid position, the instructions of that explorer will be terminated with a result: Invalid position CURRENT_POSITION: **Ex: Invalid position -1 3 N**

* Are considered to be valid explorer data: a group of two lines containing the explorer position and its instructions. If any of these data is missing, the program will not create the explorer.

* If the input file contains invalid data, the program will stop with an error messaging indicating what happens.

# Other apps
I have two variations of this program. The first one is written in Haskell, you can see it [here](https://github.com/WennderSantos/turtlechallange). The other one is also written in Clojure and expose a web api, you can see it [here](https://github.com/WennderSantos/robotsanddinosaurs).