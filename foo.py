import os

def find_empty_folders(directory):
    """Find and list all empty folders in a given directory."""
    empty_folders = []

    # Traverse through the directory
    for dirpath, dirnames, filenames in os.walk(directory):
        # If a folder contains neither sub-folders nor files, it's empty
        if not dirnames and not filenames:
            empty_folders.append(dirpath)

    return empty_folders

def main():
    folder_path = os.getcwd()#input("Enter the path of the folder: ")
    
    if not os.path.exists(folder_path) or not os.path.isdir(folder_path):
        print("Provided path is not a valid directory!")
        return
    
    empty_folders = find_empty_folders(folder_path)
    
    if empty_folders:
        print("Empty folders:")
        for folder in empty_folders:
            print(folder)
    else:
        print("No empty folders found!")

if __name__ == "__main__":
    main()
