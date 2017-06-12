<?php
namespace MirrorApiBundle\Controller;


use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;

trait ControllerTrait
{
    public function userNotFound()
    {
        return new JsonResponse(['message' => 'User not found'], Response::HTTP_NOT_FOUND);
    }

    public function moduleNotFound()
    {
        return new JsonResponse(['message' => 'Module not found'], Response::HTTP_NOT_FOUND);
    }

    public function wrongOwner()
    {
        return new JsonResponse(['message' => 'Wrong owner'], Response::HTTP_FORBIDDEN);
    }

    public function convertRequestSnakeCaseToCamelCase(&$request) {
        $tempo = [];
        foreach ($request->request as $key=>$value) {
            $tempo[$key] = lcfirst(str_replace(' ', '',ucwords(str_replace('_', ' ', $key))));
        }

        foreach ($tempo as $oldKey=>$newKey) {
            $request->request->set($newKey, $request->request->get($oldKey));
        }
    }
}