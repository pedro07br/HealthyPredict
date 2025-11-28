from fastapi import FastAPI, File, UploadFile
import tensorflow as tf
import numpy as np
from PIL import Image
import io

app = FastAPI()

model = tf.keras.models.load_model("../saved_model/meu_modelo.keras")

class_names = [
    'abacaxi','banana', 'batata', 'batata_doce', 'cebola',
    'goiaba','laranja', 'limao', 'maca','morango','pepino',
    'tomate', 'uva'
] 

@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    content = await file.read()
    image_data = Image.open(io.BytesIO(content)).resize((224,224))
    img_array = np.expand_dims(np.array(image_data) / 255.0, axis=0)

    pred = model.predict(img_array)
    resultado = class_names[np.argmax(pred)]

    return {"classe": resultado}
