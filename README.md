# README

This is a tool I made to download my favorite webcomics, so I could have them
when I was disconnected from the internet.

## Supported Webcomics

Currently it supports the following:
* Captain SNES
* Casey and Andy
* El Goonish Shive
* Girl Genius
* Order of the Stick
* Piled Higher and Deeper
* Sandra and Woo
* Saturday Morning Breakfast Cereal
* Schlock Mercenary
* The Last Days of Foxhound
* The Punchline is Machismo
* XKCD
* VG Cats

Adding new webcomics can be done by implementing the interface specified in
com.joshuasnider.comicgetter.ComicGetter.

## To Run
mvn compile
mvn exec:java -Dexec.mainClass="com.joshuasnider.comicgetter.ComicGetter"

## Proposed Features
Features I'm thinking of adding, but haven't yet.
* Add title text to images.
* Automatically detect file ending.
* GUI that lets you select which webcomics to download.

## Proposed Webcomics
I'm thinking of adding support for the following webcomics:
* Dilbert
* Erfworld
* Existential Comics
* Grrl Power
* Penny Arcade
