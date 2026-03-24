package cn.ms.dm.core.utils;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author LouMT
 * @name MacAddressUtil
 * @date 2025-08-08 15:48
 * @email lmtemail163@163.com
 * @description
 */
public class MacAddressUtil {
    /**
     * 获取本机MAC地址或IP地址
     *
     * @param ipAddress true返回IP地址，false返回MAC地址
     * @return IP地址或MAC地址
     */
    @SneakyThrows
    public static String getMacAddress(boolean ipAddress) {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            // 跳过虚拟或回环接口
            if (networkInterface.isVirtual() || networkInterface.isLoopback()) {
                continue;
            }

            byte[] mac = networkInterface.getHardwareAddress();
            if (mac == null) {
                continue;
            }

            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress inetAddress = addresses.nextElement();
                String ip = inetAddress.getHostAddress();

                // 跳过IPv6地址、特定IP段和回环地址
                if (ip.contains(":") || ip.startsWith("221.231.") || inetAddress.isLoopbackAddress()) {
                    continue;
                }

                // 优先获取公网地址，其次获取内网地址
                if (!inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()) {
                    return ipAddress ? ip : formatMacAddress(mac);
                }

                if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()) {
                    return ipAddress ? ip : formatMacAddress(mac);
                }
            }
        }

        return null;
    }

    /**
     * 格式化MAC地址为标准格式
     *
     * @param mac MAC地址字节数组
     * @return 格式化后的MAC地址
     */
    private static String formatMacAddress(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            String hex = Integer.toHexString(mac[i] & 0xFF);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(getMacAddress(true));
    }
}
