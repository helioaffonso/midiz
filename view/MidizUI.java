package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import kdTree.ArvoreKD;

import model.vo.Entrada;
import model.vo.Resultado;
import control.BuscaControl;
import exception.EntradaInvalidaException;


public class MidizUI {

	private JTable tabelaResultados; 
	private JPanel panelResultados;
	private Entrada entrada;
	private List listaResultados;
	private ArvoreKD arvoreNotas;
	private ArvoreKD arvoreDuracoes;
	private JFrame frame;
	
	int iResultsPorPagina = 5;
	int iPaginaAtual = 1;
	int iControlNota = 1;
    private JTextField txN1,txN2,txN3,txN4,txN5,txN6,txN7,txN8,txD1,txD2,txD3,txD4,txD5,txD6,txD7,txD8,txErro;
    
    public MidizUI(ArvoreKD arvoreN, ArvoreKD arvoreD) 
    {
    	// TODO: isso é uma mãozada
        this.arvoreNotas = arvoreN;
        this.arvoreDuracoes = arvoreD;
        
    	final MidiIO midiSynth = new MidiIO();
        midiSynth.open();
        
        frame = new JFrame("MIDIZ"); 
        
        JButton btBuscar = new JButton("Buscar");
        JLabel lbMIDIZ = new JLabel("MIDIZ"); 
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        midiSynth.setFocusable(true);
        
        JPanel panelSynth = new JPanel();
        panelSynth.add( midiSynth );
        
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbConst = new GridBagConstraints();
        
        frame.getContentPane().setLayout( gbLayout );
        
        gbConst.fill = GridBagConstraints.CENTER;
        gbConst.gridx = 0;
        gbConst.gridy = 0;
        gbConst.ipady = 60;
        frame.add( lbMIDIZ,gbConst );
        
        gbConst.gridx = 0;
        gbConst.gridy = 1;
        gbConst.ipadx = 30;
        gbConst.ipady = 60;
        gbConst.fill = GridBagConstraints.HORIZONTAL;    
        frame.add( midiSynth,gbConst );
        
        gbConst.gridx = 0;
        gbConst.gridy = 2;
        gbConst.ipadx = 0;
        gbConst.ipady = 0;
        frame.add( getPanelNotasDuracoes(),gbConst );
        
        gbConst.fill = GridBagConstraints.CENTER;
        gbConst.gridx = 0;
        gbConst.gridy = 3;
        gbConst.ipadx = 0;
        gbConst.ipady = 0;
        frame.add( btBuscar,gbConst );

        gbConst.fill = GridBagConstraints.HORIZONTAL; 
        gbConst.ipady = 60;
        gbConst.gridx = 0;
        gbConst.gridy = 5;
        
    	panelResultados = new JPanel();

    	GridBagLayout gb = new GridBagLayout();
    	GridBagConstraints gbcResult = new GridBagConstraints();

    	panelResultados.setLayout(gb);

   		tabelaResultados = new JTable();
   		tabelaResultados.setModel(montaResultados()); 
    	
   		gbcResult.gridx = 0;
   		gbcResult.gridy = 0;
        panelResultados.add(tabelaResultados,gbcResult);
        
        frame.add( panelResultados,gbConst );
                
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int w = 760;
        int h = 450;
        
        frame.setLocation(screenSize.width/2 - w/2, screenSize.height/2 - h/2);
        
        btBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
              buscar();
              Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
              
              int w = 760;
              int h = 650;
              frame.setLocation(screenSize.width/2 - w/2, screenSize.height/2 - h/2);
              tabelaResultados.setModel(montaResultados()); 
            }
          });

        frame.pack();
        frame.setSize(w, h);
        frame.setVisible(true);
    }
    
    private JPanel getPanelNotasDuracoes()
    {
        JLabel lbNotas = new JLabel("Notas:");
        JLabel lbDuracoes = new JLabel("Durações:");
        JLabel lbErro = new JLabel("Erro:");
    	        
        txN1 = new JTextField(5);
        txN2 = new JTextField(5);
        txN3 = new JTextField(5);
        txN4 = new JTextField(5);
        txN5 = new JTextField(5);
        txN6 = new JTextField(5);
        txN7 = new JTextField(5);
        txN8 = new JTextField(5);

        txD1 = new JTextField(5);
        txD2 = new JTextField(5);
        txD3 = new JTextField(5);
        txD4 = new JTextField(5);
        txD5 = new JTextField(5);
        txD6 = new JTextField(5);
        txD7 = new JTextField(5);
        txD8 = new JTextField(5);
        
        txErro = new JTextField(5);
        
    	JPanel panelNotas = new JPanel();

    	GridBagLayout gbNotas = new GridBagLayout();
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	panelNotas.setLayout( gbNotas );
    	
    	// Notas
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	panelNotas.add(lbNotas,gbc);

    	gbc.gridx = 1;
    	panelNotas.add(txN1,gbc);

    	gbc.gridx = 2;
    	panelNotas.add(txN2,gbc);
    	
    	gbc.gridx = 3;
    	panelNotas.add(txN3,gbc);
    	
    	gbc.gridx = 4;
    	panelNotas.add(txN4,gbc);
    	
    	gbc.gridx = 5;
    	panelNotas.add(txN5,gbc);
    	
    	gbc.gridx = 6;
    	panelNotas.add(txN6,gbc);
    	
    	gbc.gridx = 7;
    	panelNotas.add(txN7,gbc);
    	
    	gbc.gridx = 8;
    	panelNotas.add(txN8,gbc);

    	// Durações    	
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	panelNotas.add(lbDuracoes,gbc);
    	
    	gbc.gridx = 1;
    	panelNotas.add(txD1,gbc);

    	gbc.gridx = 2;
    	panelNotas.add(txD2,gbc);
    	
    	gbc.gridx = 3;
    	panelNotas.add(txD3,gbc);
    	
    	gbc.gridx = 4;
    	panelNotas.add(txD4,gbc);
    	
    	gbc.gridx = 5;
    	panelNotas.add(txD5,gbc);
    	
    	gbc.gridx = 6;
    	panelNotas.add(txD6,gbc);
    	
    	gbc.gridx = 7;
    	panelNotas.add(txD7,gbc);
    	
    	gbc.gridx = 8;
    	panelNotas.add(txD8,gbc);
    	
    	// Erro
    	gbc.gridx = 0;
    	gbc.gridy = 2;
    	panelNotas.add(lbErro,gbc);

    	gbc.gridx = 1;
    	panelNotas.add(txErro,gbc);
    	
    	return panelNotas;
    }
    
    private JPanel getPanelResultados()
    {

        
        return panelResultados;
    }
    
    private TableModel montaResultados()
    {
    	TableModel dataModel = new DefaultTableModel( listaResultados == null ?  0: iResultsPorPagina +1,4);
    	
    	if( listaResultados != null && listaResultados.size() > 0 )
    	{
        	dataModel = new DefaultTableModel(iResultsPorPagina +1,4);
        	
            dataModel.setValueAt("Música", 0, 0);
            dataModel.setValueAt("Artista", 0, 1);
            dataModel.setValueAt("Posição", 0, 2);
            dataModel.setValueAt("Instrumento", 0, 3);
    	        
            Resultado result;
    	    
	        for( int i = (iPaginaAtual * iResultsPorPagina)-iResultsPorPagina; i < listaResultados.size() && i < iPaginaAtual * iResultsPorPagina; i++ )
	        {
	        	result = new Resultado();
	        	result = (Resultado) listaResultados.get(i);
	        	 
	            dataModel.setValueAt(result.getNomeMusica(), i+1, 0);
	            dataModel.setValueAt(result.getArtista(), i+1, 1);
	            dataModel.setValueAt(result.getPosicao(), i+1, 2);
	            dataModel.setValueAt(result.getInstrumento(), i+1, 3);
	        }

    	}
	        
    	return dataModel;
    }  
    
    private void buscar()
    {
    	BuscaControl busca = new BuscaControl(arvoreNotas,arvoreDuracoes);
    	
    	entrada = new Entrada(  txN1.getText(),txN2.getText(),txN3.getText(),txN4.getText(),
    							txN5.getText(),txN6.getText(),txN7.getText(),txN8.getText(),
    							txD1.getText(),txD2.getText(),txD3.getText(),txD4.getText(),
    							txD5.getText(),txD6.getText(),txD7.getText(),txD8.getText(),txErro.getText() );
    	
    	try
    	{
    		listaResultados = busca.realizaConsulta(entrada);
    	}
    	catch( EntradaInvalidaException e )
    	{
    		exibeMensagem( "Busca inválida." );
    	}
    }
    
    // TODO
    private void exibeMensagem( String sMsg )
    {
    	System.out.println(sMsg);
    }
    
    public class MidiIO extends JPanel implements ControlContext {

    	static final long serialVersionUID = -1l;
        final int PROGRAM = 192;
        final int NOTEON = 144;
        final int NOTEOFF = 128;
        final int SUSTAIN = 64;
        final int REVERB = 91;
        final int ON = 0, OFF = 1;
        final Color jfcBlue = new Color(204, 204, 255);
        Sequencer sequencer;
        Sequence sequence;
        Synthesizer synthesizer;
        Instrument instruments[];
        ChannelData channels[];
        ChannelData cc;    // current channel
        JSlider veloS, presS, bendS, revbS;
        JCheckBox soloCB, monoCB, muteCB, sustCB; 
        Vector keys = new Vector();
        Vector whiteKeys = new Vector();
        Map keyMap;
        JTable table;
        Piano piano;
        Track track;
        long startTime;

        public MidiIO() {
            setLayout(new BorderLayout());

            JPanel pp = new JPanel(new BorderLayout());
            keyMap = getKeyMap();
            piano = new Piano();
            
            this.addKeyListener(new TecladoListener());
            
            pp.add(piano);
            
            add(pp);
        }
        
        public void open() {
            try {
                if (synthesizer == null) {
                    if ((synthesizer = MidiSystem.getSynthesizer()) == null) {
                        System.out.println("getSynthesizer() failed!");
                        return;
                    }
                } 
                synthesizer.open();
                sequencer = MidiSystem.getSequencer();
                sequence = new Sequence(Sequence.PPQ, 10);
            } catch (Exception ex) { ex.printStackTrace(); return; }

            Soundbank sb = synthesizer.getDefaultSoundbank();
    	if (sb != null) {
                instruments = synthesizer.getDefaultSoundbank().getInstruments();
                synthesizer.loadInstrument(instruments[0]);
            }
            MidiChannel midiChannels[] = synthesizer.getChannels();
            channels = new ChannelData[midiChannels.length];
            for (int i = 0; i < channels.length; i++) {
                channels[i] = new ChannelData(midiChannels[i], i);
            }
            cc = channels[0];

        }

        public void close() {
            if (synthesizer != null) {
                synthesizer.close();
            }
            if (sequencer != null) {
                sequencer.close();
            }
            sequencer = null;
            synthesizer = null;
            instruments = null;
            channels = null;
            
        }

        public void createShortEvent(int type, int num) {
            ShortMessage message = new ShortMessage();
            try {
                long millis = System.currentTimeMillis() - startTime;
                long tick = millis * sequence.getResolution() / 500;
                message.setMessage(type+cc.num, num, cc.velocity); 
                MidiEvent event = new MidiEvent(message, tick);
                track.add(event);
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        public Key getKey(Point point) {
            for (int i = 0; i < keys.size(); i++) {
                if (((Key) keys.get(i)).contains(point)) {
                    return (Key) keys.get(i);
                }
            }
            return null;
        }

        public Key getKey(int keyCode) {
            for (int i = 0; i < keys.size(); i++) {
                if (((Key) keys.get(i)).kTecla == keyCode) {
                    return (Key) keys.get(i);
                }

            }
            return null;
        }
        
        public Map getKeyMap()
        {
        	Map keyMap = new HashMap(); 
        	keyMap.put(Integer.valueOf(57), new Character('a'));
        	keyMap.put(Integer.valueOf(59), new Character('s'));
        	keyMap.put(Integer.valueOf(60), new Character('d'));
        	keyMap.put(Integer.valueOf(62), new Character('f'));
        	keyMap.put(Integer.valueOf(64), new Character('g'));
        	keyMap.put(Integer.valueOf(65), new Character('h'));
        	keyMap.put(Integer.valueOf(67), new Character('j'));
        	keyMap.put(Integer.valueOf(69), new Character('k'));
        	keyMap.put(Integer.valueOf(71), new Character('l'));
        	keyMap.put(Integer.valueOf(72), new Character('ç'));
        	
        	keyMap.put(Integer.valueOf(58), new Character('w'));
        	keyMap.put(Integer.valueOf(61), new Character('r'));
        	keyMap.put(Integer.valueOf(63), new Character('t'));
        	keyMap.put(Integer.valueOf(66), new Character('u'));
        	keyMap.put(Integer.valueOf(68), new Character('i'));
        	keyMap.put(Integer.valueOf(70), new Character('o'));
        	
        	return keyMap;
        }

        /**
         * Black and white keys or notes on the piano.
         */
        class Key extends Rectangle {
            int noteState = OFF;
            public int kNum;
            public int kTecla;
            public Key(int x, int y, int width, int height, int num) {
                super(x, y, width, height);
                kNum = num;
                if( keyMap.get(Integer.valueOf(kNum)) != null )
                {
                	Character c = (Character) keyMap.get(Integer.valueOf(kNum)); 
                	kTecla = c.charValue();
                }
            }
            public boolean isNoteOn() {
                return noteState == ON;
            }
            public void on() {
                setNoteState(ON);
                cc.channel.noteOn(kNum, cc.velocity);
            }
            public void off() {
                setNoteState(OFF);
                cc.channel.noteOff(kNum, cc.velocity);
            }
            public void setNoteState(int state) {
                noteState = state;
            }
        } // End class Key

        /**
         * Piano renders black & white keys and plays the notes for a MIDI 
         * channel.  
         */
        class Piano extends JPanel implements MouseListener {

            Vector blackKeys = new Vector();
            public Key prevKey;
            final int kw = 16, kh = 80;

            public Piano() {
                setLayout(new BorderLayout());
                setPreferredSize(new Dimension(42*kw, kh+1));
                int transpose = 24;  
                int whiteIDs[] = { 0, 2, 4, 5, 7, 9, 11 }; 
              
                for (int i = 0, x = 0; i < 6; i++) {
                    for (int j = 0; j < 7; j++, x += kw) {
                        int keyNum = i * 12 + whiteIDs[j] + transpose;
                        whiteKeys.add(new Key(x, 0, kw, kh, keyNum));
                    }
                }
                for (int i = 0, x = 0; i < 6; i++, x += kw) {
                    int keyNum = i * 12 + transpose;
                    blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+1));
                    blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+3));
                    x += kw;
                    blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+6));
                    blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+8));
                    blackKeys.add(new Key((x += kw)-4, 0, kw/2, kh/2, keyNum+10));
                }
                keys.addAll(blackKeys);
                keys.addAll(whiteKeys);

                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseMoved(MouseEvent e) {
                        Key key = getKey(e.getPoint());
                        if (prevKey != null && prevKey != key) {
                            prevKey.off();
                        } 
                        /*if (key != null && prevKey != key) {
                            key.on();
                        }*/
                        prevKey = key;
                        repaint();
                    }
                });
            
                this.setFocusable(true);
                addMouseListener(this);
            }

            public void mousePressed(MouseEvent e) {
                prevKey = getKey(e.getPoint());
                if (prevKey != null) {
                	setValorNota( piano.prevKey.kNum );
                	prevKey.on();
                    repaint();
                }
            }
            public void mouseReleased(MouseEvent e) { 
                if (prevKey != null) {
                    prevKey.off();
                    repaint();
                }
            }
            public void mouseExited(MouseEvent e)  { }
            public void mouseClicked(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { } 
            
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                Dimension d = getSize();

                g2.setBackground(getBackground());
                g2.clearRect(0, 0, d.width, d.height);

                g2.setColor(Color.white);
                g2.fillRect(0, 0, 42*kw, kh);

                for (int i = 0; i < whiteKeys.size(); i++) {
                    Key key = (Key) whiteKeys.get(i);
                    if (key.isNoteOn()) {
                        g2.setColor(jfcBlue);
                        g2.fill(key);
                    }
                    g2.setColor(Color.black);
                    g2.draw(key);
                }
                for (int i = 0; i < blackKeys.size(); i++) {
                    Key key = (Key) blackKeys.get(i);
                    if (key.isNoteOn()) {
                        g2.setColor(jfcBlue);
                        g2.fill(key);
                        g2.setColor(Color.black);
                        g2.draw(key);
                    } else {
                        g2.setColor(Color.black);
                        g2.fill(key);
                    }
                }
            }
        } // End class Piano

        class TecladoListener implements KeyListener
        {
            public void keyTyped(KeyEvent e) {
                piano.prevKey = getKey(e.getKeyChar());
                if (piano.prevKey != null) {
                	piano.prevKey.on();
                    repaint();
                }        
            }        
            
            public void keyPressed(KeyEvent e) {
            	piano.prevKey = getKey(e.getKeyChar());
                if (piano.prevKey != null) {
                	setValorNota( piano.prevKey.kNum );
                	piano.prevKey.on();
                    repaint();
                }
            }
            public void keyReleased(KeyEvent e) { 
                if (piano.prevKey != null) {
                	piano.prevKey.off();
                    repaint();
                }
            }
            public void mouseExited(MouseEvent e) { 
                if (piano.prevKey != null) {
                	piano.prevKey.off();
                    repaint();
                    piano.prevKey = null;
                }
            }
        }

        /**
         * Stores MidiChannel information.
         */
        class ChannelData {

            MidiChannel channel;
            boolean solo, mono, mute, sustain;
            int velocity, pressure, bend, reverb;
            int row, col, num;
     
            public ChannelData(MidiChannel channel, int num) {
                this.channel = channel;
                this.num = num;
                velocity = pressure = bend = reverb = 64;
            }

        } // End class ChannelData

    } 
    
    private void setValorNota( int aNota )
    {
    	switch( iControlNota )
    	{
    		case 1:
    			txN1.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 2:
    			txN2.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 3:
    			txN3.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 4:
    			txN4.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 5:
    			txN5.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 6:
    			txN6.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 7:
    			txN7.setText(Integer.toString(aNota));
    			iControlNota++;
    			break;
    		case 8:
    			txN8.setText(Integer.toString(aNota));
    			iControlNota = 1;
    			break;    			
    	}
    }
    
}
