App Name: Copy Aubie

Description: Copy Aubie is a Simon-inspired game where the player must repeat a sequence that grows
    longer as the player repeats the sequence correctly. The app allows the player to configure the
    difficulty which adjusts the speed and allows the player to mute the sound of the buttons.
    If the player is lost, they may use a hint that causes the sequence to repeat. When the player loses,
    the app shows them how many sequences they entered correctly.

Build Instructions: None

Known Bugs:
    - There was a bug where one of the MediaPlayer objects entered an illegal state
        due to some asynchronous bug. I believe this has since been fixed, but if
        it hasn't it occurs only rarely when rotating the screen while the sequence
        is being played.
    - The MediaPlayer objects sometimes behave unexpectedly and cause a runtime exception.
        This has to do with prepareAsync() being called in the wrong state, but I cannot figure
        out how this occurs. It doesn't help that this bug is extremely hard to replicate.

Miscellaneous:
    - Screen orientation is locked for the splash screen only. Screen orientation
        may be changed after the main activity is launched.
    - Colors of the app icon are Auburn's official colors. War Eagle!