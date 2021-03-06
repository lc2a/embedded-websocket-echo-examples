package org.eclipse.jetty.demo.jsr.annotated;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

@ServerEndpoint("/echo")
public class EchoSocket
{
    private static final Logger LOG = Log.getLogger(EchoSocket.class);
    private Session session;
    private RemoteEndpoint.Async remote;

    @OnClose
    public void onWebSocketClose(CloseReason close)
    {
        this.session = null;
        this.remote = null;
        LOG.info("WebSocket Close: {} - {}",close.getCloseCode(),close.getReasonPhrase());
    }

    @OnOpen
    public void onWebSocketOpen(Session session)
    {
        this.session = session;
        this.remote = this.session.getAsyncRemote();
        LOG.info("WebSocket Connect: {}",session);
        this.remote.sendText("You are now connected to " + this.getClass().getName());
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        LOG.warn("WebSocket Error",cause);
    }

    @OnMessage
    public String onWebSocketText(String message)
    {
        LOG.info("Echoing back text message [{}]",message);
        // Using shortcut approach to sending messages.
        // You could use a void method and use remote.sendText()
        return message;
    }
}
