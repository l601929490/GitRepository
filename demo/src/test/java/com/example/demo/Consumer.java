//package test;
//
//import com.rabbitmq.client.*;
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//public class Consumer {
//	//private static final Logger logger = LogUtil.getLogger(Consumer.class);
//	private ConnectionFactory factory;//连接工厂
//	private Connection connection;//连接对象
//	private Channel channel;//通道对象
//	private String EXCHANGE_NAME;//队列名称
//
//    /**
//     * 连接MQ-Server
//     *
//     * @param ip    192.168.139.132
//     * @param port  5672
//     * @param uName test
//     * @param psw   test
//     * @param qName rabbitMQ_test
//     * @throws IOException
//     * @throws TimeoutException
//     */
//    public void connectMQServer(String ip, int port, String uName, String psw, String exchangeName) throws IOException, TimeoutException {
//    	//创建连接工厂
//		factory = new ConnectionFactory();
//		//设置RabbitMQ相关信息
//		factory.setHost(ip);
//		factory.setPort(port);
//		factory.setUsername(uName);
//		factory.setPassword(psw);
//		//设置断线自动重连
//		factory.setAutomaticRecoveryEnabled(true);
//		//创建一个新的连接
//		if(connection != null && connection.isOpen()) {
//			connection.close();
//		}
//		connection = factory.newConnection();
//		//创建一个通道
//		if(channel != null && channel.isOpen()) {
//			channel.close();
//		}
//		EXCHANGE_NAME = exchangeName;
//		channel = connection.createChannel();
//		//声明交换机和类型 可用的交换机类型Direct Topic Headers Fanout
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
//        //创建一个非持久的、唯一的且自动删除的队列且队列名称由服务器随机产生
//        String queueName = channel.queueDeclare().getQueue();
//        // 为交换机指定队列，设置binding
//        channel.queueBind(queueName, EXCHANGE_NAME, "");
//        /**
//         * DefaultConsumer类实现了Consumer接口，通过传入一个频道，
//         * 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
//         */
//        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope,
//                                       AMQP.BasicProperties properties, byte[] body)
//                    throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println("message is " + message);
//            }
//        };
//        //自动回复队列应答 -- RabbitMQ中的消息确认机制
//        channel.basicConsume(queueName, true, consumer);
//    }
//    
//    /**
//     * 关闭此连接及其所有通道
//     */
//    public void closeConnect() {
//        try {
//            if (channel != null) {
//                channel.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public static void main(String[] args) {
//    	Consumer test = new Consumer();
//    	try {
//			test.connectMQServer("49.232.157.28", 5672, "admin", "admin", "S2C");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TimeoutException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//
//}
