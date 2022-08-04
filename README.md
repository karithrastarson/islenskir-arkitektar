# Íslenskir Arkitektar
Project that aims to collect information about Icelandic architects
and their work and make it more accessible to the general public.

# Running the service
## System Requirements
* Docker Compose
* Maven
* Java

##Starting the Service
1. Navigate to root of project
2. Run `docker-compose up`
3. Run `mvn spring-boot:run`

# Endpoints
After running the service locally, navigate to [localhost:8484/swagger-ui.html](http://localhost:8484/swagger-ui.html) to browse endpoints

# TODO:
* Nice to have: uploading status
* Full URL for assets
* Nice to have:  Cache cache cache. No frequent updates, so easy to cache results. Worth it?

# MVP requirements for front facing user interface
* Search endpoint for architects that **supports pagination**
* Search endpoint for addresses that **supports pagination**
* Get all architects alphabetically, pagination
* Get all buildings alphabetically, paginated
* Get architect endpoint. With list of addresses
* Get building endpoint. With list of assets.

# MVP requirements for admin facing user interface

Select architect modal:
* search architect by name (DONE)

Select building modal:
* search building by address (DONE)

Other:
* Link architect to building (DONE)
* Upload multiple files on building object. (DONE)
* Edit building information (DONE)
* Unlink architect (DONE)
* Delete image by id (NICE TO HAVE)
* Create new architect (DONE)
* Edit Architect information (DONE)
* Remove Architect (DONE)

* Load 'stadfangaskrá' into db