package com.example.cats;

import java.io.File;
import java.nio.ByteBuffer;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Steganog
{
	public Steganog()
	{
	}
	
	private boolean encodeImage(String folder, String img, String stegImg, String msg, String extIn)
	{
		String file = folder+"/" + img + "." + extIn;
		//BufferedImage imgOrig = getImage(file);
		Bitmap imgBitmap = BitmapFactory.decodeFile(file);
		System.out.println("Image read");
		
		imgBitmap = add_msg(imgBitmap,msg);
		
		//Send imgBitmap to enemy
		
		return true;
	}
	
	private Bitmap add_msg(Bitmap imgIn, String msg)
	{
		//Convert everything to byte arrays
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		imgIn.compress(Bitmap.CompressFormat.JPEG,100,stream);
		byte[] byteArray = stream.toByteArray();
		//ByteBuffer byteBuff = ByteBuffer.allocate(imgIn.getByteCount());
		//imgIn.copyPixelsToBuffer(byteBuff);
		//ByteBuffer bytes = byteBuffer.ToArray<byte>();
		byte msgArray[] = msg.getBytes();
		byte len[] = bit_conversion(msgArray.length);
		
		try
		{
			encode_text(byteArray,len,0);
			encode_text(byteArray,msgArray,32); //32 offset for size above
		}
		catch(Exception e)
		{
			System.out.println("Error encoding text");
		}
		
		return imgIn;
	}
	
	//Generates proper byte format
	private byte[] bit_conversion(int i)
	{
		byte byte3 = (byte)((i & 0xFF000000) >>> 24);
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16);
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8);
		byte byte0 = (byte)((i & 0x000000FF));
		
		return( new byte[]{byte3,byte2,byte1,byte0});
	}
	
	private byte[] encode_text(byte[] image, byte[] addition, int offset)
	{
		//Does it fit in the image
		if(addition.length + offset > image.length)
		{
			throw new IllegalArgumentException("Image not long enough");
		}
		
		//Loop through each byte in addition
		for(int i=0; i<addition.length;++i)
		{
			//Loop through bits
			int add = addition[i];
			for(int bit=7;bit>=0;--bit,++offset)
			{
				int b = (add >>> bit) & 1;
				
				//Change last bit of byte to last of addition
				image[offset] = (byte)((image[offset] & 0xFE) | b);
			}
		}
		
		return image;
	}

}