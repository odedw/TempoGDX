TempoGDX is an open source metronome app built with libGDX.

![Imgur](http://i.imgur.com/v7h7KvB.png)

## Background

I was working on a music sequencer type app built with libGDX, and trying out a number of different metronome ideas to find which implementation will give the most accurate results. I couldn't find a good reference for a metronome, and noticed there aren't a lot of good open source projects built with libGDX. I decided to create a separate app and open source it.

## Collaboration

Collaborations, pull requests and issues are welcome! Help me make this a great libGDX app that can serve as a reference to other developers.  
There are lots of areas that can be way better:

**Support More Platforms** - currently the project supports Desktop and Android. Web and iOS can be added.

**Better Metronome** - I tried a few different implementations for the Metronome class until I reached the current one. I'm sure it can be better than it is. One of my goals that developers can take the `Metronome` & `BeatEventListener` classes as is and drop them in a project. 

**Better UI** - I feel that libGDX is a bit archaic in it's UI paradigm compared to other UI systems (like HTML/CSS, WPF and others), but I'm also new to libGDX so I'm sure the UI can be much better than it is.

**Accessibility** - Searching for "libGDX accessibility" yields zero relevant results. Not even sure how to do it with libGDX.

**Tests** - Self explanatory.