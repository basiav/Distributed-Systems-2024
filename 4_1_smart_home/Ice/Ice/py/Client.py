import sys
import traceback

import Ice

import Demo
# from generated.Demo import BulbPrx, PrinterPrx
# from generated.calculator_ice import BulbPrx

print(Ice.stringVersion())


def print_available_devices():
    map_name_proxy.keys()
    print("\nAvailable devices:")
    for i, key in enumerate(map_name_proxy.keys()):
        print(f"[{i}] {key}")


def get_available_device_operations():
    print(f"Available operations for [{chosen_device_name}]:")
    ops = [item for item in dir(device) if
           not (("ice" in item) or ("_" in item) or ("Async" in item) or ("Cast" in item))]
    for i, op_ in enumerate(ops):
        print(f"({i}) {op_}")

    return ops


def get_device_category():
    return chosen_device_proxy_name.split("/")[0]


if __name__ == '__main__':
    status = 0
    ic = None

    map_name_proxy = {
        # "bathroom bulb": "Bulb/bathroomBulb",
        "bathroom bulb": "Bulb/bathroom",
>>>>>>>>> Temporary merge branch 2
        "mothers room bulb": "Bulb/mothersRoomBulb",
        "home office color printer": "Printer/homeOfficeColorPrinter",
        "spare office grayscale printer": "Printer/spareOfficeGrayScalePrinter"
    }

    map_device_address = {
        "bathroom bulb": "127.0.0.1",
        "kitchen bulb": "127.0.0.2",
        "mothers room bulb": "127.0.0.2",
        "home office color printer": "127.0.0.1",
        "spare office grayscale printer": "127.0.0.2"
    }

    try:
        while True:
            print_available_devices()
            input_ = input("\nChoose the device number or press `X` to exit:\n")
            if input_ == "X":
                break
            elif not input_.isnumeric():
                print("Incorrect input")
                continue
            chosen_device_idx = int(input_)

            if chosen_device_idx >= len(map_name_proxy):
                print("Incorrect device number, try again...")
                continue

            chosen_device_name = list(map_name_proxy.keys())[chosen_device_idx]
            chosen_device_proxy_name = list(map_name_proxy.values())[chosen_device_idx]
            ic = Ice.initialize(sys.argv)

            print("ADDRESS " + map_device_address[chosen_device_name])
            base = ic.stringToProxy(
                f"{chosen_device_proxy_name} : tcp -h {map_device_address[chosen_device_name]} -p 10000")

            print(f"base: {base}")
            device = Demo.BulbPrx.checkedCast(base) \
                if get_device_category() == "Bulb" else Demo.PrinterPrx.checkedCast(base)
            print(f"device: {device}")
            if not device:
                raise RuntimeError("Invalid proxy")

            operations = get_available_device_operations()
            while True:
                args = None

                op = input("Choose operation number or press `X` to exit:\n")
                if op == "X":
                    break

                elif not op.isnumeric():
                    print("Incorrect input")
                    continue

                fun = getattr(device, operations[int(op)])
                if fun.__code__.co_varnames[1:-1]:
                    print("Insert listed arguments:")
                    for item in fun.__code__.co_varnames[1:-1]:
                        print(f"-> {item}")
                        if item == "color":
                            print(f"\t{Demo.Color._enumerators}")
                            color = input()
                            args = Demo.Color.valueOf(int(color))
                        else:
                            args = input()
                            args = int(args) if args and args.isdigit() else args

                try:
                    returned = fun(args)
                    if returned:
                        print(returned)
                except Exception as err:
                    traceback.print_exc()


    except:
        traceback.print_exc()
        status = 1

    if ic:
        # Clean up
        try:
            ic.destroy()
        except:
            traceback.print_exc()
            status = 1

    sys.exit(status)