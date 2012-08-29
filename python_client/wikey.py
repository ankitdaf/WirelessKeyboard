#!/usr/bin/env python

"""
Copyright Ankit Daftery,2012
"""

import sys,ConfigParser
from socket import socket, AF_INET, SOCK_STREAM
    
config = ConfigParser.RawConfigParser()
configfile = 'wireless_keyboard.cfg'

def send(text,host):
    """
    Handles the sockets and data packet transmission
    """
    mySocket = socket( AF_INET, SOCK_STREAM )
    mySocket.connect(host)
    mySocket.sendto(text,host)
    mySocket.close()
    
def main(argv=None):
    """
    Calls the shots
    """
    if(not config.read(configfile)):
        create_config()
    host = fetch_config()
    if(len(sys.argv)>1):
        msg = ' '.join(sys.argv[1:])
        send(msg,host)
    
def create_config():
    """
    Generate configuration files if they don't exist
    """
    config.add_section('wifi')
    config.set('wifi','ip_address','192.168.2.3')
    config.set('wifi','port',26015)
    with open('wireless_keyboard.cfg','wb') as configfile:
        config.write(configfile)

def fetch_config():
    """
    Retrieve configuration from file
    """
    config.read(configfile)
    SERVER_IP = config.get("wifi","ip_address")
    PORT = int(config.get("wifi","port"))
    return (SERVER_IP,PORT)

if __name__ == "__main__":
  sys.exit(main())
