from cProfile import label
import numpy as np
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import pandas as pd


rule = '2'

df = pd.read_json('results\\rule'+ rule + '\\aliveCells10.json')

x10 = df['t']
y10 = df['alive']

df = pd.read_json('results\\rule'+ rule + '\\aliveCells20.json')

x20 = df['t']
y20 = df['alive']

df = pd.read_json('results\\rule'+ rule + '\\aliveCells30.json')

x30 = df['t']
y30 = df['alive']

df = pd.read_json('results\\rule'+ rule + '\\aliveCells70.json')

x40 = df['t']
y40 = df['alive']

df = pd.read_json('results\\rule'+ rule + '\\aliveCells80.json')

x50 = df['t']
y50 = df['alive']

df = pd.read_json('results\\rule'+ rule + '\\aliveCells90.json')

x60 = df['t']
y60 = df['alive']

plt.plot(x10, y10, 'r', label="N =10%", marker="o", markersize=2, linewidth=1)
plt.plot(x20, y20, 'b', label="N =20%", marker="o", markersize=2, linewidth=1)
plt.plot(x30, y30, 'g', label="N =30%", marker="o", markersize=2, linewidth=1)
plt.plot(x40, y40, 'c', label="N =70%", marker="o", markersize=2, linewidth=1)
plt.plot(x50, y50, 'm', label="N =80%", marker="o", markersize=2, linewidth=1)
plt.plot(x60, y60, 'y', label="N =90%", marker="o", markersize=2, linewidth=1)
plt.xlabel("Tiempo (iteraciones)")
plt.ylabel("Celdas")
plt.legend(loc="best")
plt.show()