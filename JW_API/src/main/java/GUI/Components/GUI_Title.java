package GUI.Components;

import Packets.ReflectionAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class GUI_Title {
    private static Class<?> packetClass = null;
    private static Class<?> componentClass = null;
    private static Class<?> packetTabClass = null;
    private static Class<?> serializerClass = null;
    private static Constructor<?> packetConstructor = null;
    private static Constructor<?> packetTabConstructor = null;
    private static Class<Enum> enumTitleAction = null;

    public static void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        packetClass = ReflectionAPI.getNmsClass("PacketPlayOutTitle");
        componentClass = ReflectionAPI.getNmsClass("IChatBaseComponent");
        serializerClass = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
        enumTitleAction = (Class<Enum>) ReflectionAPI.getNmsClass("PacketPlayOutTitle$EnumTitleAction");
        try {
            packetConstructor = packetClass.getConstructor(enumTitleAction, componentClass, int.class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (subtitle != null) {
            Object subTitleSer;
            Object subTitlePacket;
            try {
                subTitleSer = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");
                subTitlePacket = packetConstructor.newInstance(enumTitleAction.getEnumConstants()[1], subTitleSer, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
                ReflectionAPI.sendPacket(p, subTitlePacket);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println(enumTitleAction.getEnumConstants());
                e.printStackTrace();
                System.out.println(enumTitleAction.getEnumConstants());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.out.println(enumTitleAction.getEnumConstants());
            }
        }
        packetClass = ReflectionAPI.getNmsClass("PacketPlayOutTitle");
        componentClass = ReflectionAPI.getNmsClass("IChatBaseComponent");
        serializerClass = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
        enumTitleAction = (Class<Enum>) ReflectionAPI.getNmsClass("PacketPlayOutTitle$EnumTitleAction");
        try {
            packetConstructor = packetClass.getConstructor(enumTitleAction, componentClass, int.class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (subtitle != null) {
            Object subTitleSer;
            Object subTitlePacket;
            try {
                subTitleSer = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");
                subTitlePacket = packetConstructor.newInstance(enumTitleAction.getEnumConstants()[1], subTitleSer, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
                ReflectionAPI.sendPacket(p, subTitlePacket);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println(enumTitleAction.getEnumConstants());
                e.printStackTrace();
                System.out.println(enumTitleAction.getEnumConstants());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.out.println(enumTitleAction.getEnumConstants());
            }
        }
        if (title != null) {
            Object titleSer;
            Object titlePacket;
            try {
                titleSer = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + title + "\"}");
                titlePacket = packetConstructor.newInstance(enumTitleAction.getEnumConstants()[0], titleSer, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
                ReflectionAPI.sendPacket(p, titlePacket);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendTabTitle(Player p, String header, String footer) {
        packetTabClass = ReflectionAPI.getNmsClass("PacketPlayOutPlayerListHeaderFooter");
        componentClass = ReflectionAPI.getNmsClass("IChatBaseComponent");
        serializerClass = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
        try {
            packetTabConstructor = packetTabClass.getConstructor(componentClass);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        }
        if (header == null) header = "";
        if (footer == null) footer = "";
        Object tabTitle;
        Object tabFoot;
        Object headerPacket;
        try {
            tabTitle = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + header + "\"}");
            tabFoot = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + footer + "\"}");
            headerPacket = packetTabConstructor.newInstance(tabTitle);
            Field field = headerPacket.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(headerPacket, tabFoot);
            ReflectionAPI.sendPacket(p, headerPacket);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}


