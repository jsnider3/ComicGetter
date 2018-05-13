# README

This is a tool I made to download my favorite webcomics, so I could have them
when I was disconnected from the internet.

## Supported Webcomics

Currently it supports the following:
* The Last Days of Foxhound
* Girl Genius
* Order of the Stick
* The Punchline is Machismo
* Schlock Mercenary
* XKCD
* Saturday Morning Breakfast Cereal

Adding new webcomics can be done by implementing the interface specified interface
com.joshuasnider.comicgetter.ComicGetter.

## To Run
mvn compile
mvn exec:java