package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

public class DialogBox {
	protected static final String ACTION_END_DIALOG = "END_DIALOG";
	protected SpriteBatch spriteBatch = new SpriteBatch();
	protected BitmapFont font = Assets.font;
	protected boolean visible;
	protected boolean useGeneratedPortrait = true;
	protected boolean dialogEnded = false;
	protected float offX, offY;
	
	protected Sprite bg = Assets.dialogBoxBG;
	protected Sprite talkingPortrait;
	protected String text;

	// number of characters to break the string for the next line
	protected static final int MAX_CHARS = 200;
	
	// offsets for portrait / text
	protected static final int START_PORTRAIT_X = 100;
	protected static final int START_PORTRAIT_Y = 75;
	protected static final int START_TEXT_X = 260;
	protected static final int START_TEXT_Y = 200;
	
	public DialogBox() {
		this(null,"");
		useGeneratedPortrait = false;
	}
	
	public DialogBox(Sprite talkingPortrait, String text) {
		this.talkingPortrait = talkingPortrait;
		this.text = text;
		visible = false;
		
		if (text != null) {
			if (text.length() > MAX_CHARS) {
				System.out.println("WARNING: Dialog Box string length too long! Clipping to 200 characters");
				text = text.substring(0, 200);
			}
		}
	}
	
	public void setTalkingPortrait(Sprite talkingPortrait) {
		this.talkingPortrait = talkingPortrait;
	}
	
	public void setPortraitOffsets(float offX, float offY) {
		this.offX = offX;
		this.offY = offY;
	}
	
	public void setUseGeneratedPortrait(boolean useGeneratedPortrait) {
		this.useGeneratedPortrait = useGeneratedPortrait;
	}
	
	private void getGeneratedPortrait() {
		int colonIndex = text.indexOf(':');
		if (colonIndex > 0) {
			String talker = text.substring(0, colonIndex);
			if (talker.equals("Schrodinger") || talker.equals("Unknown Man")) {
				if (!Globals.K) {
					if (talkingPortrait != Assets.schrodingerE) {
						talkingPortrait = Assets.schrodingerE;
						bg = Assets.dialogSchrodinger;
					}
				} else {
					if (talkingPortrait != Assets.dave) {
						talkingPortrait = Assets.dave;
						bg = Assets.dialogDave;
					}
				}
			} else if (talker.equals("Robert")) {
				if (talkingPortrait != Assets.robertPortrait) {
					talkingPortrait = Assets.robertPortrait;
					bg = Assets.dialogRobert;
				}
			} else if (talker.equals("Cat")) {
				if (talkingPortrait != Assets.catE) {
					talkingPortrait = Assets.catE;
					bg = Assets.dialogCat;
				}
			} else if (talker.equals("Schrodinger (Radio)")) {
				// TODO: Replace with Radio
				if (talkingPortrait != Assets.schrodingerE) {
					talkingPortrait = Assets.schrodingerE;
					bg = Assets.dialogSchrodinger;
				}
			} else if (talker.equals("PARSE")) {
				text = text.substring(colonIndex+1);
				if (talkingPortrait != Assets.robertPortrait) {
					talkingPortrait = Assets.robertPortrait;
					bg = Assets.dialogRobert;
				}
			} else if (talker.equals("ACTION")) {
				String action = text.substring(colonIndex+1);
				if (action.trim().equals(ACTION_END_DIALOG)) {
					bg = Assets.dialogRobert;
					dialogEnded = true;
				}
				text = "";
			}
		}
	}
	
	public void setText(String text) {
		Assets.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if (text != null) {
			this.text = text;
			
			if (text.length() > MAX_CHARS) {
				System.out.println("WARNING: Dialog Box string length too long! Clipping to 200 characters");
				text = text.substring(0, 200);
			}
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isDialogEnded() {
		return dialogEnded;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public void update() {
		if (useGeneratedPortrait) getGeneratedPortrait();
		if (visible) draw();
	}
	
	protected void draw() {
		spriteBatch.draw(bg, 0, 0);
		//spriteBatch.draw(talkingPortrait, START_PORTRAIT_X+offX, START_PORTRAIT_Y+offY);
		font.setColor(0, 0, 0, 1.0f);
		font.drawWrapped(spriteBatch, text, START_TEXT_X, START_TEXT_Y, 700);
	}
}
