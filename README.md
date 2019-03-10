# babadum2anki
Turn the assets of Babadum into an Anki deck

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