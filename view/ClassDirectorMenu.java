package view;

import controller.ClassDirectorController;
import model.TeachingRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 课程主任菜单 (Class Director Menu)
 * 提供课程主任用户界面，处理用户输入和显示输出
 */
public class ClassDirectorMenu {
    private final ClassDirectorController controller;
    private final Scanner scanner;

    /**
     * 构造函数
     *
     * @param controller 课程主任控制器
     * @param scanner 输入扫描器
     */
    public ClassDirectorMenu(ClassDirectorController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }


}