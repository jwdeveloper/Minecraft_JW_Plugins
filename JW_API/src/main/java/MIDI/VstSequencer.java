package MIDI;


import javax.sound.midi.MidiEvent;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VstSequencer implements Runnable {
    private static final float ShortMaxValueAsFloat = (float) Short.MAX_VALUE;
    public static float BPM = 120f, PPQ = 2f;
    private static float oneTick = 60000f / (BPM * PPQ);

    private List<MidiEvent> currentEvents = new ArrayList<MidiEvent>();
    private long startTime = System.currentTimeMillis(), elapsedTicks = 0;
    //private JVstHost2 vst;

   // private final float[][] fInputs;
    private final float[][] fOutputs;
    private final byte[] bOutput;
    private int blockSize;
    private int numOutputs;
    private int numAudioOutputs;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;

    public VstSequencer(
            //JVstHost2 vst
            ) {
      //  this.vst = vst;

   //     numOutputs = vst.numOutputs();
        numAudioOutputs = Math.min(2, numOutputs);

      //      blockSize = vst.getBlockSize();
     //   fInputs = new float[vst.numInputs()][blockSize];
        fOutputs = new float[numOutputs][blockSize];
        bOutput = new byte[numAudioOutputs * blockSize * 2];

      //  audioFormat = new AudioFormat((int) vst.getSampleRate(), 16,
           //     numAudioOutputs, true, false);
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                audioFormat);

        sourceDataLine = null;
        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat, bOutput.length);
            sourceDataLine.start();
        } catch (LineUnavailableException lue) {
            lue.printStackTrace(System.err);
            System.exit(1);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            sourceDataLine.drain();
            sourceDataLine.close();
        } finally {
            super.finalize();
        }
    }

    private byte[] floatsToBytes(float[][] fData, byte[] bData) {
        int index = 0;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < numAudioOutputs; j++) {
                short sval = (short) (fData[j][i] * ShortMaxValueAsFloat);
                bData[index++] = (byte) (sval & 0x00FF);
                bData[index++] = (byte) ((sval & 0xFF00) >> 8);
            }
        }
        return bData;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                break;
            }
            if (System.currentTimeMillis() - startTime >= oneTick) {
                elapsedTicks++;
                startTime = System.currentTimeMillis();
            }
           // vst.processReplacing(fInputs, fOutputs, blockSize);
            sourceDataLine.write(floatsToBytes(fOutputs, bOutput), 0,    bOutput.length);


            Iterator<MidiEvent> it = currentEvents.iterator();
            while (it.hasNext()) {
                MidiEvent currentEvent = it.next();
                long eventTime = currentEvent.getTick() - elapsedTicks;
                if (eventTime <= 0) {
                 //   vst.queueMidiMessage((ShortMessage) currentEvent   .getMessage());

                    it.remove();
                }
            }
        }

    }

    public void queueEvents(List<MidiEvent> events) {
        for (MidiEvent event : events) {
            event.setTick(event.getTick() + elapsedTicks);
        }
        currentEvents.addAll(events);
    }

    public void queueEvent(MidiEvent event) {
        event.setTick(event.getTick() + elapsedTicks);
        currentEvents.add(event);
    }
}