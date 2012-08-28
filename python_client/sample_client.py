import sys
from socket import socket, AF_INET, SOCK_STREAM

SERVER_IP   = '192.168.2.3'
PORT_NUMBER = 26015
host = (SERVER_IP,PORT_NUMBER)

def send(text):
    mySocket = socket( AF_INET, SOCK_STREAM )
    mySocket.connect(host)
    mySocket.sendto(text,host)
    mySocket.close()
    
sys.exit()
 
