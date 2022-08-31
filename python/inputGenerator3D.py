from numpy import random
import math
import argparse
import random as rnd

# Generates the dynamic file configuration given:
#   - filename -> Name of the dynamic file to be used
#   - area_length -> Total length of the area of study
def generate_dynamic_file(filename, points, area_length):
    f = open(filename, 'w')
    # We provide only the dynamic configuration at time 0
    f.write('0\n')

    # Adding the randomly generated
    for p in points:
        if len(p) == 3:
            f.write('{} {} {}\n'.format(p[0], p[1], p[2]))
    f.close()

def generate_static_file(filename, area_length, dimensions, percentage):
    f = open(filename, 'w')

    # Adding the dimensions of the area
    f.write('{} {} {}\n'.format(area_length, area_length, area_length))

    # Adding the percentage
    f.write('{}\n'.format(percentage))

    f.close()

# Generates 3D coordinates
def generate_3d_coordinates(n):
    points = []
    for x in range(n):
        for y in range(n):
            for z in range(n):
                points.append([x, y, z])
    return points
    
# Offsets the 3d points in order to center them in the space
def offset_3d_coordinates(points, arealength, inputAreaLength):
    offset = arealength - int((arealength - inputAreaLength)/2) - inputAreaLength
    for p in points:
        p[0] += offset
        p[1] += offset
        p[2] += offset
    return points

# Generate the dynamic file
def generate_files(area_length, dimensions, percentage, points):
    generate_static_file('./input/static.txt', area_length, dimensions, percentage)
    generate_dynamic_file('./input/dynamic.txt', points, area_length)

# main() function
def main():
    # Command line args are in sys.argv[1], sys.argv[2] ..
    # sys.argv[0] is the script name itself and can be ignored
    # parse arguments
    parser = argparse.ArgumentParser(description="Generates random input for the Game of Life")

    # add arguments
    parser.add_argument('-L', dest='area_length', required=True)
    parser.add_argument('-l', dest='input_area_length', required=True)
    parser.add_argument('-p', dest='percentage', required=True)
    parser.add_argument('-d', dest='dimensions', required=True)
    args = parser.parse_args()

    # Validations
    if int(args.dimensions) != 3:
        raise Exception("Invalid dimensions, valid number is 3D(3)")

    if float(args.percentage) < 0 or float(args.percentage) > 100:
        raise Exception("Invalid fill percentage")

    if int(args.area_length) <= int(args.input_area_length):
        raise Exception("Invalid area lengths")

    # Generation of points list
    if int(args.dimensions) == 3:
        points = generate_3d_coordinates(int(args.input_area_length))


    # Shuffling the points
    rnd.shuffle(points)

    # Number of points to be chosen
    target_points_count = int(len(points) * (float(args.percentage)/100))

    # Choosing the percentage of points
    chosen_points = points[0:target_points_count]

    # Offsetting the generated points
    if int(args.dimensions) == 3:
        chosen_points = offset_3d_coordinates(chosen_points, int(args.area_length), int(args.input_area_length))


    # Generating the files
    generate_files(int(args.area_length), int(args.dimensions), float(args.percentage), chosen_points)

# call main
if __name__ == '__main__':
    main()