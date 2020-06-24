# babadum2anki

Turn the assets of Babadum into an Anki deck using a Java program.
Requires Java 8.

# Generating the Anki deck and resources

1. In App.java, change the language in the main to the desired language. (e.g. PORTUGUESE)
2. Run the App class.

# Importing the Anki deck

After having run the main, the following folders will have been created : 

```bash
|-  /data/
    |-  /portuguese/
        |-  /anki/
            |- /collection.media/           Contains the images (.svg) and audio (.ogg) files.
            |- anki-deck.txt                The Anki deck.
        |-  /ogg/
        |-  /properties/
        |-  /svg/
```

1. Copy the collection.media content into your Anki media folder.
2. Import the deck located at /data/anki/portuguese/anki-deck.txt 

The fields are as follows :
```bash
FIELD 1 : Image (FRONT)         The svg image tag.        
FIELD 2 : Word + Audio (BACK)   The word and the audio file.
FIELD 3 : Difficulty (LEVEL)    The complexity of the word from 0 to 9.
FIELD 4 : Audio file (AUDIO)    The audio file.
```

The deck is sorted by increasing difficulty.

# Example

[Anki Shared Spanish deck](https://ankiweb.net/shared/info/222240580)

```
mvn compile
mvn package
mvn install assembly:assembly
```

```
java -cp target/BabadumExtractor-1.0-SNAPSHOT.jar:/home/aaron/.m2/repository//org/apache/httpcomponents/httpclient/4.5.7/httpclient-4.5.7.jar:/home/aaron/.m2/repository//org/apache/httpcomponents/httpcore/4.4.11/httpcore-4.4.11.jar:/home/aaron/.m2/repository//commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/home/aaron/.m2/repository//org/json/json/20180813/json-20180813.jar:~/.m2/repository//commons-fileupload/commons-fileupload/1.3.1/commons-fileupload-1.3.1.jar:/home/aaron/.m2/repository//commons-io/commons-io/2.6/commons-io-2.6.jar wprg.babadumextractor.App
```
