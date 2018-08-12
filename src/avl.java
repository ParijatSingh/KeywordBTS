public class avl {
    
    Node root;

    private class Node{
    	
    	// These attributes of the Node class will not be sufficient for those attempting the AVL extra credit.
    	// You are free to add extra attributes as you see fit, but do not remove attributes given as it will mess with help code.
        String keyword;
        Record record;
        int size;
        Node l;
        Node r;
        Node parent;
        int height;

        Node(String k){
        	// TODO Instantialize a new Node with keyword k.
        	keyword = k;
        }

        void update(Record r){
        	//TODO Adds the Record r to the linked list of records. You do not have to check if the record is already in the list.
        	//HINT: Add the Record r to the front of your linked list.
        	if(record == null) record = r;
        	else{
        		Record tmp = record;
        		r.next = tmp;
        		record = r;
        	}
        }       
    }

    public avl(){
        this.root = null;
    }
    
    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id, fd.author, fd.title, null);
        //TODO Write a recursive insertion that adds recordToAdd to the list of records for the node associated
        //with keyword. If there is no node, this code should add the node.
        if(root == null){
        	root = new Node(keyword);
        	root.record = recordToAdd;
        	return;
        }
        //this is a recursive function
        insertNode(keyword, root, recordToAdd);
    }
    
    /**
     * Recursive method to insert a node and re-balance the avl tree
     * @param keyword
     * @param node  - This is the node being inspected for comparison
     * @param recordToAdd
     */
    public void insertNode(String keyword, Node nd, Record recordToAdd){
    	if(keyword.equalsIgnoreCase(nd.keyword)){
    		nd.update(recordToAdd);
    		return;
    	}else if(keyword.compareToIgnoreCase(nd.keyword) < 0){
    		if(nd.l == null){
    			nd.l = new Node(keyword);
    			nd.l.parent = nd;
    		}
    		insertNode(keyword, nd.l, recordToAdd);
    		int leftHeight = -1;
    		int rightHeight = -1;
    		if (nd.l != null)
    			leftHeight = nd.l.height;
    		if (nd.r != null)
    			rightHeight = nd.r.height;
    		if( leftHeight - rightHeight == 2 )
                if( keyword.compareToIgnoreCase( nd.parent.l.keyword ) < 0 )
                	nd.parent = rotateWithLeftChild( nd.parent );
                else
                	nd.parent = doubleWithLeftChild( nd.parent );
    	}else if(keyword.compareToIgnoreCase(nd.keyword) > 0){
    		if(nd.r == null){
    			nd.r = new Node(keyword);
    			nd.r.parent = nd;
    		}
    		insertNode(keyword, nd.r, recordToAdd);
    		int leftHeight = -1;
    		int rightHeight = -1;
    		if (nd.l != null)
    			leftHeight = nd.l.height;
    		if (nd.r != null)
    			rightHeight = nd.r.height;
    		if( rightHeight- leftHeight == 2 )
                if( keyword.compareToIgnoreCase( nd.parent.r.keyword ) < 0 )
                	nd.parent = rotateWithRightChild( nd.parent );
                else
                	nd.parent = doubleWithRightChild( nd.parent );
    	}
    }
    
    /**
     * Rotate binary tree node with left child.
     * This rotation is for case 1 when : The insertion was in the left subtree of the left child of Node nd
     * It update heights, then return new root.
     */
	private static Node rotateWithLeftChild(Node nd) {
		Node left = nd.l;
		nd.l = left.r;
		left.r = nd;
		int leftHeight = -1;
		int rightHeight = -1;
		if (nd.l != null)
			leftHeight = nd.l.height;
		if (nd.r != null)
			rightHeight = nd.r.height;
		nd.height = Math.max(leftHeight, rightHeight) + 1;
		leftHeight = -1;
		if (left.l != null)
			leftHeight = left.l.height;
		left.height = Math.max(leftHeight, nd.height) + 1;
		return left;
	}


    /**
     * Rotate binary tree node with right child.
     * This rotation is for case 4 when :  The insertion was in the right subtree of the right child of node nd
     * Update heights, then return new root.
     */
	private static Node rotateWithRightChild(Node nd) {
		Node right = nd.r;
		nd.r = right.l;
		right.l = nd;
		int leftHeight = -1;
		int rightHeight = -1;
		if (nd.l != null)
			leftHeight = nd.l.height;
		if (nd.r != null)
			rightHeight = nd.r.height;
		nd.height = Math.max(leftHeight, rightHeight) + 1;
		rightHeight = -1;
		if (right.r != null)
			rightHeight = right.r.height;
		right.height = Math.max(rightHeight, nd.height) + 1;
		return right;
	}

    /**
     * Double rotate binary tree node
     * This is for case 2 : The insertion was in the right subtree of the left child of node nd
     * It update heights, then return new root.
     */

	private static Node doubleWithLeftChild(Node nd) {
		nd.l = rotateWithRightChild(nd.l);
		return rotateWithLeftChild(nd);
	}

    /**
     * Double rotate binary tree node
     * This is for case 3 : The insertion was in the left subtree of the right child of node nd
     * it update heights, then return new root.
     */

	private static Node doubleWithRightChild(Node nd) {
		nd.r = rotateWithLeftChild(nd.r);
		return rotateWithRightChild(nd);
	}
    
    public boolean contains(String keyword){
    	//TODO Write a recursive function which returns true if a particular keyword exists in the bst
    	Node nd = root;
    	while(nd != null){
    		if(nd.keyword.compareToIgnoreCase(keyword) == 0){
    			return true;
    		}else if(nd.keyword.compareToIgnoreCase(keyword) < 0){
	    		nd = nd.l;
			}else if(nd.keyword.compareToIgnoreCase(keyword) > 0){
				nd = nd.r;
			}
    	}
    	return false;
    }

    public Record get_records(String keyword){
        //TODO Returns the first record for a particular keyword. This record will link to other records
    	//If the keyword is not in the bst, it should return null.
    	Node nd = root;
    	while(nd != null){
    		if(keyword.compareToIgnoreCase(nd.keyword) == 0){
    			return nd.record;
    		}else if(keyword.compareToIgnoreCase(nd.keyword) < 0){
	    		nd = nd.l;
			}else if(keyword.compareToIgnoreCase(nd.keyword) > 0){
				nd = nd.r;
			}
    	}
    	return null;
    }
    
    /**
     * This is similar method to insertNode but instead of keyword
     * it takes node to be inserted. This is also recursive method.
     * @param node
     * @param nodeToInsert
     */
    public void insertNode(Node node, Node nodeToInsert){
    	if(nodeToInsert.keyword.compareToIgnoreCase(node.keyword) < 0){
    		if(node.l == null){
    			node.l = nodeToInsert;
    		}else{
    			node = node.l;
    			insertNode(node, nodeToInsert);
    		}
    	}else if(nodeToInsert.keyword.compareToIgnoreCase(node.keyword) > 0){
    		if(node.r == null){
    			node.r = nodeToInsert;
    		}else{
    			node = node.r;
    			insertNode(node, nodeToInsert);
    		}
    	}
    }
    
    public void delete(String keyword){
    	//TODO Write a recursive function which removes the Node with keyword from the binary search tree.
    	//You may not use lazy deletion and if the keyword is not in the bst, the function should do nothing.
		//Node nodeToReInsert = null;
    	if(keyword.equalsIgnoreCase(root.keyword)){
    		Node nodeToReInsert = root.r;
    		root = root.l;
    		if(nodeToReInsert!= null) insertNode(root, nodeToReInsert);
    	}else if(keyword.compareToIgnoreCase(root.keyword) < 0){
    		deleteNode(keyword, root, root.l);
    	}else if(keyword.compareToIgnoreCase(root.keyword) > 0){
    		deleteNode(keyword, root, root.r);
    	}
    }
    
    /**
     * Recursive method to delete a node
     * @param keyword
     * @param parentNode
     * @param node - It is the current node being inspected for comparison
     */
    public void deleteNode(String keyword, Node parentNode, Node node){
    	if(node == null) return ;
    	if(keyword.equalsIgnoreCase(node.keyword)){
        	Node nodeToReInsert = null;
    		if(node == parentNode.l){
    			if(node.l != null) {
    				parentNode.l = node.l;
    				nodeToReInsert = node.r;
    			}
			}else if(node == parentNode.r){
				if(node.l != null) {
					parentNode.r = node.l;
					nodeToReInsert = node.r;
				}else{
					parentNode.r = node.r;
				}
			}
    		if(nodeToReInsert!= null) insertNode(root, nodeToReInsert);
    	}else if(keyword.compareToIgnoreCase(node.keyword) < 0){
    		parentNode = node;
    		node = node.l;
    		deleteNode(keyword, parentNode, node);
    	}else if(keyword.compareToIgnoreCase(node.keyword) > 0){
    		parentNode = node;
    		node = node.r;
    		deleteNode(keyword, parentNode, node);
    	}
		//return nodeToReInsert;
    }

    public void print(){
        print(root);
    }

    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        } 
    }
    

}
