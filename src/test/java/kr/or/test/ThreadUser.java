package kr.or.test;

class MovieThread extends Thread {
 @Override
 public void run() {
  for(int cnt=0;cnt<3;cnt++) {
   System.out.println("동영상을 재생 합니다.");
  }
  while(true) {
   System.out.println("동영상을 무한재생 합니다.");
   /*try {
    Thread.sleep(1);
   }catch(Exception e) { 
    break;
   }*/
//스레드-실행중인클래스 가 main()에서 interrupt()가 호출되었을때
   if(Thread.interrupted()) {//interrupted는 인터럽트 시킨 이벤트발생 true
    System.out.println("인터럽트가 발생 되었습니다. 반복문을 종료합니다.");
    break;//while문 빠져나가기
   }
  }
 }
}
class MusicRunnable implements Runnable {
 @Override
 public void run() {
  for(int cnt=0;cnt<3;cnt++) {
   System.out.println("음악을 재생 합니다.");
  }
 }
}
public class ThreadUser {

 public static void main(String[] args) {
  Thread threadMovie = new MovieThread();//초기값을 MovieThread클래스에서 가져옴
  threadMovie.setDaemon(true);//백그라운드로 실행설정
  threadMovie.start();
  //main() 스레드에서 1초 후 threadMovie를 종료하는 코드
  try {
   Thread.sleep(1000);//Main스레드는 1초간 중지상태
  }catch(InterruptedException e) {
  }
  threadMovie.interrupt();//무비스레드를 중지하겠다는 메서드실행.
  
  Thread threadMusic = new Thread(new MusicRunnable());
  threadMusic.start();
 }

}
