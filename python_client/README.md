Wireless Keyboard : Python Client
=================================

This folder contains simple python shell based clients for WirelessKeyboard

sample_client serves as a basic hint to the flow of communication

wikey.py is a fully working client, use it communicate to WirelessKeyboard

wireless_keyboard.cfg is a configuration file that stores wifi connection information.Ideally, that should be the only file you would need to edit to setup the connection

Invocation
----------

```
$ ./wikey.py "your message goes here"
```

Additionally, to use it as a installed command like:

```
$ wikey "your message goes here" 
```

1. Rename wikey.py to wikey
2. Copy wikey and wireless_keyboard.cfg to /usr/local/bin
3. Type away

Wishlist
--------

1. Do away with the exclamation marks in the message
2. Provide a text based options to change configuration

Project Page
------------

The project page can be found at http://ankitdaf.com/projects/WirelessKeyboard

Feel free to fork,branch out, contribute and hack away !

Ping me at me@ankitdaf.com if you wish to appreciate/criticize/contribute to the project
