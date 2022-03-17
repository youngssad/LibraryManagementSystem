package com.enjoy.book.action;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@WebServlet(urlPatterns = "/code.let",loadOnStartup = 1)
public class ValCodeServlet extends HttpServlet {
        Random random =new Random();

    private String getRandomStr(){
        String str="23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghgkmnopqrstuvwxyz";//1,0,l o
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            int index = random.nextInt(str.length());
            char letter = str.charAt(index);
            sb.append(letter);
        }
        return sb.toString();
    }

    private Color getBackColor(){
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        return new Color(red,green,blue);

    }

    private Color getForeColor(Color bgColor){
        int red = 255 - bgColor.getRed();
        int green = 255 - bgColor.getGreen();
        int blue = 255 - bgColor.getBlue();
        return new Color(red,green,blue);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("image/jpeg");
        BufferedImage bufferedImage = new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
        Graphics g  = bufferedImage.getGraphics();
        Color bgColor = getBackColor();
        g.setColor(bgColor);
        g.fillRect(0,0,80,30);
        Color foreColor = getForeColor(bgColor);
        g.setColor(foreColor);
        g.setFont(new Font("Times New Roman",Font.BOLD,26));
        String randomStr = getRandomStr();
        HttpSession session = req.getSession();
        session.setAttribute("code",randomStr);
        g.drawString(randomStr,10,28);
        for(int i=0;i<30;i++){
            g.setColor(Color.white);
            int x = random.nextInt(80);
            int y = random.nextInt(30);
            g.fillRect(x,y,1,1);
        }
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(bufferedImage,"jpeg",sos);
    }
}
