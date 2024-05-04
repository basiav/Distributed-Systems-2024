import sys
import traceback

import Ice

import Demo
# from generated.Demo import BulbPrx, PrinterPrx
# from generated.calculator_ice import BulbPrx

print("Ice version: ", Ice.stringVersion())


def print_available_devices():
    print("\nAvailable devices:")
    for i, (proxy_key,  (_, address)) in enumerate(zip(map_name_proxy.keys(), map_device_address.items())):
        print(f"[{i}] {proxy_key} on {address}")


def get_available_device_operations():
    print(f"Available operations for [{device_chosen_name}]:")
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
        # On first server
        "bathroom bulb": "Bulb/bathroomBulb",
        "bathroom detector": "Detector/bathroomDetect",
        "kitchen detector": "Detector/kitchenDetect",
        # On second server
        "living room bulb": "Bulb/livingRoomBulb",
        "bedroom detector": "Detector/bedroomDetect"
    }

    SERVER_FIRST = "127.0.0.1"
    SERVER_SECOND = "127.0.0.2"
    map_device_address = {
        # On first server
        "bathroom bulb": SERVER_FIRST,
        "bathroom detector": SERVER_FIRST,
        "kitchen detector": SERVER_FIRST,
        # On second server
        "living room bulb": SERVER_SECOND,
        "bedroom detector": SERVER_SECOND
    }

    BULB_CAT = "Bulb"
    DETECTOR_CAT = "Detector"

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

            device_chosen_name = list(map_name_proxy.keys())[chosen_device_idx]
            chosen_device_proxy_name = list(map_name_proxy.values())[chosen_device_idx]
            ic = Ice.initialize(sys.argv)

            print("ADDRESS " + map_device_address[device_chosen_name])
            base = ic.stringToProxy(
                f"{chosen_device_proxy_name} : tcp -h {map_device_address[device_chosen_name]} -p 10000")

            device = Demo.IBulbPrx.checkedCast(base) \
                if get_device_category() == BULB_CAT else Demo.IDetectorPrx.checkedCast(base)
            print(f"Device: {device}\n\n")
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
                print("fun: ", fun)
                print(f"operations[int(op)]: {operations[int(op)]}")
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