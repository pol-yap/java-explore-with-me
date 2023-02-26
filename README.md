# Explore With Me
Back-end of service for finding some events and activities companions.

## Synopsis
This is something like a very simple social network. The user publishes information about the event in which he plans to participate. Being interested, other users can send a request for their participation. The initiator of the event approves (or not) the participation of other users and thus a company for spending time together is selected

## Features
- Events can be grouped into categories
- Compilations of events can be formed
- Event moderation may be mandatory or not
- Participation approvement may be mandatory or not
- Event search by keywords, category, fee mandatory, start date
- There are three access level
  - *Public* for everyone
  - *Private* for registered users
  - *Admin* for moderation
- Only registered users can publicate events
- There is possibility to leave comments on events
- Registered users can moderate comments on theirs events
- There is separately operating service for event views statistics

## Start with Docker
### Requirements
- JDK 11
- Apache Maven
- Docker
- Docker Compose

In project directory

```mvn package```

```docker-compose up```
