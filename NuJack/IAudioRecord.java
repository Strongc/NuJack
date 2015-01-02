package NuJack;

public interface IAudioRecord {

   public void startRecording();
   public void release();
   public int read (short[] buffer, int start, int end);
}