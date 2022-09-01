import numpy as np
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import pandas as pd

df = pd.read_json('results\maxDistance50.json')

x = df['t']
y = df['maxDistance']

fig, ax = plt.subplots()
plt.plot(x, y, 'r')
plt.show()