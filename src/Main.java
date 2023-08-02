
public class Main implements Runnable {
	WorkspaceGUI gui=new WorkspaceGUI();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new Thread(new Main()).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			gui.repaint();
		}
	}

}
