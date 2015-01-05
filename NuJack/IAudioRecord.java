package NuJack;

public interface IAudioRecord {

   public void startRecording();
   public void release();
   public short[] read();
   public int getSize();
   public int read (short[] buffer, int start, int end);
}