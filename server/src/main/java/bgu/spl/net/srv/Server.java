package bgu.spl.net.srv;

import bgu.spl.net.impl.stomp.MsgEncDec;
import bgu.spl.net.impl.stomp.StompMessagingProtocolClass;

import java.io.Closeable;
import java.util.function.Supplier;

public interface Server<T> extends Closeable {

    /**
     * The main loop of the server, Starts listening and handling new clients.
     */
    void serve();

    /**
     *This function returns a new instance of a thread per client pattern server
     * @param port The port for the server socket
     * @param protocolFactory A factory that creats new MessagingProtocols
     * @param encoderDecoderFactory A factory that creats new MessageEncoderDecoder
     * @param <T> The Message Object for the protocol
     * @return A new Thread per client server
     */
    public static <T> Server<T>  threadPerClient(
            int port,
            Supplier<StompMessagingProtocolClass> protocolFactory,
            Supplier<MsgEncDec> encoderDecoderFactory) {

        return new BaseServer<T>(port, protocolFactory, encoderDecoderFactory) {
            @Override
            protected void execute(BlockingConnectionHandler<T>  handler) {
                new Thread(handler).start();
            }
        };

    }

    /**
     * This function returns a new instance of a reactor pattern server
     * @param nthreads Number of threads available for protocol processing
     * @param port The port for the server socket
     * @param protocolFactory A factory that creats new MessagingProtocols
     * @param encoderDecoderFactory A factory that creats new MessageEncoderDecoder
     * @param <T> The Message Object for the protocol
     * @return A new reactor server
     */
    public static Reactor reactor(
            int nthreads,
            int port,
            Supplier<StompMessagingProtocolClass> protocolFactory,
            Supplier<MsgEncDec> encoderDecoderFactory) {
        return new Reactor(nthreads, port, protocolFactory, encoderDecoderFactory);
    }

}
