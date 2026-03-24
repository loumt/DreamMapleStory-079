import cn.ms.dm.core.utils.StringUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LouMT
 * @name Test
 * @date 2026-02-13 13:34
 * @email lmtemail163@163.com
 * @description
 */
public class Test {


    /**
     * 处理文本文件，将每行超过80个字符的行插入换行符
     * @param inputFilePath 输入文件路径
     * @param outputFilePath 输出文件路径
     * @throws IOException 文件读写异常
     */
    public static void processTextFile(String inputFilePath, String outputFilePath) throws IOException {
        // 读取原始文件
        List<String> lines = Files.readAllLines(Paths.get(inputFilePath), StandardCharsets.UTF_8);

        // 处理每一行
        List<String> processedLines = new ArrayList<>();

        for (String line : lines) {
            line = line.replaceAll("qiushu.cc \\[天火大道小说]", "");
            line = line.replaceAll("<strong>求书网WWW.Qiushu.cc</strong>", "");

            if (line.length() <= 80) {
                processedLines.add(line);
            } else {
                // 行长度超过80，需要分割
                List<String> splitLines = splitLine(line, 80);
                List<String> appendSplitLines = splitLines.stream().filter(StringUtil::isNotBlank).map(sl -> "    " + sl).collect(Collectors.toList());
                processedLines.addAll(appendSplitLines);
            }
        }
        // 写入新文件
        Files.write(Paths.get(outputFilePath), processedLines, StandardCharsets.UTF_8);

        System.out.println("文件处理完成！");
        System.out.println("原文件行数: " + lines.size());
        System.out.println("处理后行数: " + processedLines.size());
    }

    /**
     * 按指定长度分割字符串行
     * @param line 原始行
     * @param maxLength 最大长度
     * @return 分割后的行列表
     */
    private static List<String> splitLine(String line, int maxLength) {
        List<String> result = new ArrayList<>();

        // 如果行本身就很长，按固定长度分割
        int start = 0;
        while (start < line.length()) {
            int end = Math.min(start + maxLength, line.length());

            // 如果不是最后一段，尝试在合适的位置断行（避免在单词中间断开）
            if (end < line.length()) {
                // 寻找最近的空格或标点符号进行断行
                int breakPoint = findBreakPoint(line, start, end);
                if (breakPoint > start) {
                    end = breakPoint;
                }
            }

            result.add(line.substring(start, end));
            start = end;
        }

        return result;
    }

    /**
     * 寻找合适的断行点
     * @param line 整行文本
     * @param start 起始位置
     * @param end 结束位置
     * @return 断行位置
     */
    private static int findBreakPoint(String line, int start, int end) {
        // 优先寻找空格
        for (int i = end - 1; i > start; i--) {
            if (line.charAt(i) == ' ') {
                return i + 1; // 包含空格本身
            }
        }

        // 如果找不到空格，寻找标点符号
        for (int i = end - 1; i > start; i--) {
            char ch = line.charAt(i);
            if (ch == '，' || ch == '。' || ch == '！' || ch == '？' ||
                    ch == '；' || ch == '：' || ch == ',' || ch == '.') {
                return i + 1;
            }
        }

        // 如果都找不到，就在指定位置断行
        return end;
    }

    /**
     * 演示方法使用
     */
    public static void main(String[] args) {
        String inputFile = "D:\\IntelliJ IDEA 2024.3\\game-workspace\\DreamMapleStory-079\\doc\\雪中悍刀行.txt";
        String outputFile = "D:\\IntelliJ IDEA 2024.3\\game-workspace\\DreamMapleStory-079\\doc\\雪中悍刀行_处理后.txt";

        try {
            processTextFile(inputFile, outputFile);
            System.out.println("处理后的文件保存在: " + outputFile);
        } catch (IOException e) {
            System.err.println("文件处理出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
