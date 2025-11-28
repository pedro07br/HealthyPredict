import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D, Dropout
from tensorflow.keras.applications import MobileNetV2
import numpy as np
from tensorflow.keras.preprocessing import image

# Caminho do dataset
dataset_path = r"C:\Users\User\Desktop\IA_py\sem_classificacao"

# Classes
class_names = [
    'abacaxi','banana', 'batata', 'batata_doce', 'cebola',
    'goiaba','laranja', 'limao', 'maca','morango','pepino',
    'tomate', 'uva'
] 

# Data augmentation
train_datagen = ImageDataGenerator(
    rescale=1./255,
    validation_split=0.2,
    rotation_range=20,
    width_shift_range=0.2,
    height_shift_range=0.2,
    shear_range=0.2,
    zoom_range=0.2,
    horizontal_flip=True,
    fill_mode='nearest'
)

train_generator = train_datagen.flow_from_directory(
    dataset_path,
    target_size=(224,224),
    batch_size=16,
    class_mode='categorical',
    subset='training',
    shuffle=True
)

validation_generator = train_datagen.flow_from_directory(
    dataset_path,
    target_size=(224,224),
    batch_size=16,
    class_mode='categorical',
    subset='validation',
    shuffle=True
)

# Transfer Learning: MobileNetV2
base_model = MobileNetV2(weights='imagenet', include_top=False, input_shape=(224,224,3))
base_model.trainable = False  

x = base_model.output
x = GlobalAveragePooling2D()(x)
x = Dense(128, activation='relu')(x)
x = Dropout(0.5)(x)
predictions = Dense(len(class_names), activation='softmax')(x)

model = Model(inputs=base_model.input, outputs=predictions)
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
model.summary()

import math

steps_per_epoch = math.ceil(train_generator.samples / train_generator.batch_size)
validation_steps = math.ceil(validation_generator.samples / validation_generator.batch_size)

history = model.fit(
    train_generator,
    steps_per_epoch=steps_per_epoch,
    validation_data=validation_generator,
    validation_steps=validation_steps,
    epochs=15
)

# Função para testar imagem
def prever_imagem(caminho_imagem):
    img = image.load_img(caminho_imagem, target_size=(224,224))
    img_array = image.img_to_array(img)/255.0
    img_array = np.expand_dims(img_array, axis=0)
    
    pred = model.predict(img_array)
    classes = list(train_generator.class_indices.keys())
    print("Classe prevista:", classes[np.argmax(pred)])

prever_imagem(r"C:\Users\User\Desktop\IA_py\sem_classificacao\banana\banana_sem_ju (1).jpg")

MODEL_PATH = r"C:\Users\User\Desktop\IA_py\saved_model\meu_modelo.keras"
model.save(MODEL_PATH)
print("Modelo salvo com sucesso!")