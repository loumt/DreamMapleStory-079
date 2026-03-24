import cn.ms.dm.maple.netty.ByteArrayByteStream;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;
import java.awt.Point;

/**
 * @author LouMT
 * @name LittleEndianAccessorExample
 * @date 2026-02-27 16:56
 * @email lmtemail163@163.com
 * @description
 */
public class LittleEndianAccessorExample {

    public static void main(String[] args) {
        // 方法一：直接使用字节数组创建
     demonstrateWithByteArray();

        // 方法二：使用 MaplePacketLittleEndianWriter 创建数据
//        demonstrateWithPacketWriter();
    }

    /**
     * 方法一：直接使用字节数组创建 LittleEndianAccessor
     */
    public static void demonstrateWithByteArray() {
        System.out.println("=== 方法一：使用字节数组 ===");

        // 创建测试数据
        byte[] testData = new byte[]{
                0x01, 0x02, 0x03, 0x04,           // int: 0x04030201 = 67305985
                0x10, 0x20,                       // short: 0x2010 = 8208
                0x48, 0x65, 0x6C, 0x6C, 0x6F,     // ASCII string "Hello"
                0x05, 0x00,                       // 字符串长度 (5)
                0x57, 0x6F, 0x72, 0x6C, 0x64,     // Maple ASCII string "World"
                0x64, 0x00, 0x2C, 0x01            // Position: x=100, y=300
        };

        // 创建 ByteArrayByteStream
        ByteArrayByteStream byteStream = new ByteArrayByteStream(testData);

        // 创建 LittleEndianAccessor
        LittleEndianAccessor lea = new LittleEndianAccessor(byteStream);

        // 读取数据
        int intValue = lea.readInt();
        short shortValue = lea.readShort();
        String asciiString = lea.readAsciiString(5);
        String mapleString = lea.readMapleAsciiString();
        Point position = lea.readPos();

        // 输出结果
        System.out.println("读取的整数: " + intValue);
        System.out.println("读取的短整数: " + shortValue);
        System.out.println("读取的ASCII字符串: " + asciiString);
        System.out.println("读取的Maple字符串: " + mapleString);
        System.out.println("读取的位置: (" + position.x + ", " + position.y + ")");
        System.out.println("当前读取位置: " + lea.getPosition());
        System.out.println("剩余可读字节数: " + lea.available());
        System.out.println();
    }

    /**
     * 方法二：使用 MaplePacketLittleEndianWriter 创建数据然后读取
     */
    public static void demonstrateWithPacketWriter() {
        System.out.println("=== 方法二：使用 MaplePacketLittleEndianWriter ===");

        // 使用 MaplePacketLittleEndianWriter 写入数据
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        // 写入各种类型的数据
        mplew.writeInt(123456789);          // 写入整数
        mplew.writeShort((short) 12345);    // 写入短整数
        mplew.writeAsciiString("Test");     // 写入ASCII字符串
        mplew.writeMapleAsciiString("MapleStory"); // 写入Maple格式字符串
        mplew.writePos(new Point(100, 200)); // 写入位置信息
        mplew.writeLong(9876543210L);       // 写入长整数
//        mplew.writeFloat(3.14159f);         // 写入浮点数
//        mplew.writeDouble(2.718281828);     // 写入双精度浮点数

        // 获取字节数组
        byte[] packetData = mplew.getPacket();

        // 创建 ByteArrayByteStream 和 LittleEndianAccessor
        ByteArrayByteStream byteStream = new ByteArrayByteStream(packetData);
        LittleEndianAccessor lea = new LittleEndianAccessor(byteStream);

        // 读取数据（按写入顺序）
        int intVal = lea.readInt();
        short shortVal = lea.readShort();
        String asciiStr = lea.readAsciiString(4);
        String mapleStr = lea.readMapleAsciiString();
        Point pos = lea.readPos();
        long longVal = lea.readLong();

        // 输出结果
        System.out.println("读取的整数: " + intVal);
        System.out.println("读取的短整数: " + shortVal);
        System.out.println("读取的ASCII字符串: " + asciiStr);
        System.out.println("读取的Maple字符串: " + mapleStr);
        System.out.println("读取的位置: (" + pos.x + ", " + pos.y + ")");
        System.out.println("读取的长整数: " + longVal);
        System.out.println("数据十六进制表示: " + lea.toString());
        System.out.println();
    }

    /**
     * 演示其他读取方法
     */
    public static void demonstrateOtherMethods() {
        System.out.println("=== 其他方法演示 ===");

        // 创建包含多种数据的字节数组
        byte[] mixedData = new byte[]{
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,  // 8个字节用于测试
                0x41, 0x42, 0x43, 0x00,                           // 带null终止的字符串 "ABC"
                0x10, 0x20, 0x30, 0x40                            // 4个字节用于回读测试
        };

        ByteArrayByteStream byteStream = new ByteArrayByteStream(mixedData);
        LittleEndianAccessor lea = new LittleEndianAccessor(byteStream);

        // 读取前4个字节
        byte[] firstFour = lea.read(4);
        System.out.println("读取前4字节: " + bytesToHex(firstFour));

        // 回退2个字节
        lea.unRead(2);
        System.out.println("回退2字节后位置: " + lea.getPosition());

        // 重新读取
        byte[] reRead = lea.read(4);
        System.out.println("重新读取4字节: " + bytesToHex(reRead));

        // 读取null终止字符串
        String nullTermStr = lea.readNullTerminatedAsciiString();
        System.out.println("Null终止字符串: " + nullTermStr);

        // 使用last方法读取
        int lastInt = lea.readLastInt();
        System.out.println("最后读取的整数: " + lastInt);
    }

    /**
     * 辅助方法：将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }
}