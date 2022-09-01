from cProfile import label
import numpy as np
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import pandas as pd

rule = '2'

df = pd.read_json('results\\rule'+ rule + '\\maxDistance10.json')

x10 = df['t']
y10 = df['maxDistance']

df = pd.read_json('results\\rule'+ rule + '\\maxDistance20.json')

x20 = df['t']
y20 = df['maxDistance']

df = pd.read_json('results\\rule'+ rule + '\\maxDistance30.json')

x30 = df['t']
y30 = df['maxDistance']

df = pd.read_json('results\\rule'+ rule + '\\maxDistance70.json')

x70 = df['t']
y70 = df['maxDistance']

df = pd.read_json('results\\rule'+ rule + '\\maxDistance80.json')

x80 = df['t']
y80 = df['maxDistance']

df = pd.read_json('results\\rule'+ rule + '\\maxDistance90.json')

x90 = df['t']
y90 = df['maxDistance']

plt.plot(x10, y10, 'r', label="N =10%", marker="o", markersize=2, linewidth=1)
plt.plot(x20, y20, 'b', label="N =20%", marker="o", markersize=2, linewidth=1)
plt.plot(x30, y30, 'g', label="N =30%", marker="o", markersize=2, linewidth=1)
plt.plot(x70, y70, 'c', label="N =70%", marker="o", markersize=2, linewidth=1)
plt.plot(x80, y80, 'm', label="N =80%", marker="o", markersize=2, linewidth=1)
plt.plot(x90, y90, 'y', label="N =90%", marker="o", markersize=2, linewidth=1)
plt.xlabel("Tiempo (iteraciones)")
plt.ylabel("Distancia (celdas)")
plt.legend(loc="best")
plt.show()