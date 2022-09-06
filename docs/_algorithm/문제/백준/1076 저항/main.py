colors = {'black': 0,
          'brown': 1,
          'red': 2,
          'orange': 3,
          'yellow': 4,
          'green': 5,
          'blue': 6,
          'violet': 7,
          'grey': 8,
          'white': 9}

inputs = []
inputs.append(input())
inputs.append(input())
inputs.append(input())

result = ''
for input_i in range(0, len(inputs)):
    value: int
    for color_key in colors.keys():
        if inputs[input_i] == color_key:
            value = colors[color_key]
            break

    if input_i == 0:
        result += str(value)
    elif input_i == 1:
        result += str(value)
    elif input_i == 2:
        result = str(int(result) * (10**value))

print(result)