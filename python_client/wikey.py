#!/usr/bin/env python

"""
Copyright Ankit Daftery,2012
"""

import sys,ConfigParser,time,os
from socket import socket, AF_INET, SOCK_STREAM
    
config = ConfigParser.RawConfigParser()
configfile = 'wireless_keyboard.cfg'
logfile = os.path.expanduser("~/.wikey_log.txt")

class Wikey:
  """
  A class to handle the wireless connection
  """
  host=None
  log=None  
  def __init__(self):
    """
    Initialise the connection
    """
    if(not config.read(configfile)):
      self.create_config()
    self.log=False
    self.host=self.fetch_config()
    if(len(sys.argv)>1):
      msg = ' '.join(sys.argv[1:])
      self.send(msg)    
  
  def send(self,text):
    """
    Handles the sockets and data packet transmission
    """
    mySocket = socket( AF_INET, SOCK_STREAM )
    mySocket.connect(self.host)
    if(self.log):
      logger=open(logfile,"a")
      logger.write(time.asctime()+" " + text+"\n")
      logger.close()
    mySocket.sendto(text,self.host)
    mySocket.close()
    
  def create_config(self):
    """
    Generate configuration files if they don't exist
    """
    config.add_section('wifi')
    config.set('wifi','ip_address','192.168.2.3')
    config.set('wifi','port',26015)
    config.set('wifi','log','True')
    with open('wireless_keyboard.cfg','wb') as configfile:
      config.write(configfile)

  def fetch_config(self):
      """
      Retrieve configuration from file
      """
      config.read(configfile)
      SERVER_IP = config.get("wifi","ip_address")
      PORT = int(config.get("wifi","port"))
      self.log=bool(config.get("wifi","log"))
      return (SERVER_IP,PORT)

if __name__ == "__main__":
  wikey=Wikey()
  sys.exit()
