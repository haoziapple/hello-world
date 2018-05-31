from constants import *


def lex_string(string):
    json_string = ''

    if string[0] == JSON_QUOTE:
        string = string[1:]
    else:
        return None, string

    for c in string:
        if c == JSON_QUOTE:
            return json_string, string[len(json_string) + 1:]
        else:
            json_string += c
    raise Exception('Expected end-of-string quote')


def main():
    pass


if __name__ == '__main__':
    main()