package harrypotter.model.tournament;

import harrypotter.model.character.Champion;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;

import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tournament implements TaskListener {

	private ArrayList<Champion> champions;
	private ArrayList<Spell> spells;
	private FirstTask firstTask;
	private SecondTask secondTask;
	private ThirdTask thirdTask;
	private SwapMapListener switchListener;

	public Tournament() throws IOException {

		champions = new ArrayList<Champion>();
		spells = new ArrayList<Spell>();
		loadSpells("Spells.csv");

	}

	public ArrayList<Champion> getChampions() {
		return champions;
	}

	public ArrayList<Spell> getSpells() {
		return spells;
	}

	public FirstTask getFirstTask() {
		return firstTask;
	}

	public SecondTask getSecondTask() {
		return secondTask;
	}

	public ThirdTask getThirdTask() {
		return thirdTask;
	}

	public void addChampion(Champion c) {
		champions.add(c);
	}

	private void loadSpells(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {

			String[] content = line.split(",");
			switch (content[0]) {

			case "DMG":
				spells.add(new DamagingSpell(content[1], Integer
						.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3])));
				break;

			case "HEL":
				spells.add(new HealingSpell(content[1], Integer
						.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3])));
				break;

			case "REL":
				spells.add(new RelocatingSpell(content[1], Integer
						.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3])));
				break;

			}

			line = br.readLine();

		}

		br.close();

	}

	public void beginTournament() throws IOException {

		firstTask = new FirstTask(champions);
		firstTask.setListener(this);

	}

	public void onFinishingFirstTask(ArrayList<Champion> winners)
			throws IOException {

		if (!winners.isEmpty()) {
			secondTask = new SecondTask(winners);
			secondTask.setListener(this);
		}
		try {
			switchListener.onFinishingFirstTask();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onFinishingSecondTask(ArrayList<Champion> winners)
			throws IOException, FontFormatException {

		if (!winners.isEmpty()) {
			thirdTask = new ThirdTask(winners);
			thirdTask.setListener(this);
		}
		switchListener.onFinishingSecondTask();

	}

	public void onFinishingThirdTask(Champion winner) throws IOException, FontFormatException {

		// TODO: M4
		

		switchListener.onFinishingThirdTask();
	}

	public SwapMapListener getSwitchListener() {
		return switchListener;
	}

	public void setSwitchListener(SwapMapListener switchListener) {
		this.switchListener = switchListener;
	}

}
