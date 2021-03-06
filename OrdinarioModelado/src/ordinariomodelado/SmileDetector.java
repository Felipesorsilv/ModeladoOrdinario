/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinariomodelado;

/**
 *
 * @author Felipe
 */
public class SmileDetector extends Detector
{
    private final CascadeClassifier smileHaar; // clasificadores
    
    /**
     * Constructor
     */
    public SmileDetector()
    {
        super();
        // Inicializamos los clasificadores
        smileHaar = new CascadeClassifier();
        
        // Inicializamos las propiedades
        scaleFactor = 1.05;
        minNeighbors = 4;
        
        // Cargamos los clasificadores
        loadClassifiers();
    }
    
    /**
     * Cargamos los clasificadores con los que vamos a trabajar
     */
    private void loadClassifiers()
    {
        // cargamos los clasificadores cascada
        smileHaar.load(path+"haarcascade_smile.xml");
    }
    
    /**
     * Detector de sonrisa en un rostro
     * 
     * @param m Matriz original
     * @param grayFrame Matriz original en escala de grises
     * @param faces Matriz de rostros detectados
     */
    public void smileDetector(Mat m, Mat grayFrame, MatOfRect faces)
    {
        // Matriz para los objetos detectados (ojos)
        MatOfRect smile = new MatOfRect();
        
        // Convertimos la matriz en un array (vector)
        Rect[] facesArray = faces.toArray();

        // trabajamos en cada uno de los rostros
        for(Rect rect: facesArray){
            // Obtenemos el rectangulo donde vamos a trabajar
            Rect rectCrop = new Rect(rect.x, rect.y , rect.width, rect.height);
            // Obtenemos la matriz roi
            Mat roiGray = grayFrame.submat(rectCrop);
            Mat roiColor = m.submat(rectCrop);
            
            // Obtenemos el tamaño minimo (25%)
            minSize = calcSize(roiGray, 0.25F);
            
            smileHaar.detectMultiScale(roiGray, smile, scaleFactor, minNeighbors,
                    flags, new Size(minSize, minSize), new Size());

            // Dibujamos los rectangulos para los ojos
            objectDraw(roiColor, smile, new Scalar(255, 0, 0));
        }
    }
}