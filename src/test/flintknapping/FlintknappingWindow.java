package test.flintknapping;

import javax.swing.JOptionPane;

import test.holdables.tools.Tool;

public class FlintknappingWindow {

	public Tool getTool() {
		Tool[] options = { Tool.AXE_HEAD, Tool.KNIFE_BLADE, Tool.SHOVEL_HEAD, Tool.GARBAGE };
		int n = JOptionPane.showOptionDialog(null, // parent container of JOptionPane
				"Choose which tool you would like to make", "Flintknapping",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
				options, // the titles of buttons
				options[3]);// default button title
		if(n == -1)
			return Tool.GARBAGE;
		return options[n];
	}

}
